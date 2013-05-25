package ch.bitwave.lang.delphi.mapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Stack;

import ch.bitwave.lang.delphi.ast.*;

public class DelphiSyntaxWriter extends DelphiSyntaxAdapter {

	private Writer writer;
	private String currentIndent;
	private Stack<String> indentStack;
	private boolean inStatement;
	private int scheduledBreakCount;

	private void append(final String text) {
		try {
			while (this.scheduledBreakCount > 0) {
				this.writer.append("\r\n");
				this.scheduledBreakCount--;
				if (this.scheduledBreakCount == 0) {
					writeIndent();
				}
			}
			this.writer.write(text);
		} catch (IOException e) {
			throw new RuntimeException(String.format("Failed to append text to writer due to: %s",
					e.getMessage()), e);
		}
	}

	private void newStatement() {
		if (this.inStatement) {
			append(";");
			ensureNewLine();
		}
		this.inStatement = true;
	}

	private void indent() {
		this.indentStack.push(this.currentIndent);
		this.currentIndent = this.currentIndent + "  ";
	}

	private void undent() {
		this.currentIndent = this.indentStack.pop();
	}

	private void writeIndent() {
		append(this.currentIndent);
	}

	private void ensureNewLine() {
		if (this.scheduledBreakCount == 0) {
			this.scheduledBreakCount = 1;
		}
	}

	private void ensureEmptyLine() {
		this.scheduledBreakCount = 2;
	}

	private void acceptSeparatedList(final List<? extends Node> list, final String separator) {
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				append(separator);
			}
			list.get(i).accept(this);
		}
	}

	public DelphiSyntaxWriter(final Writer writer) {
		super();
		this.writer = writer;
		this.currentIndent = "";
		this.indentStack = new Stack<String>();
	}

	public void writeNode(final Node node) {
		node.accept(this);
	}

	@Override
	public void visitAncestorDeclaration(final AncestorDeclaration node) {
		append("(");
		List<Identifier> ancestors = node.getAncestors();
		acceptSeparatedList(ancestors, ", ");
		append(")");
	}

	@Override
	public void visitArrayType(final ArrayType node) {
		append("array");
		if (!node.getQualifiers().isEmpty()) {
			acceptSeparatedList(node.getQualifiers(), ", ");
		}
		append(" of ");
		node.getComponentType().accept(this);
	}

	@Override
	public void visitAssignmentStatement(final AssignmentStatement node) {
		newStatement();
		node.getVariable().accept(this);
		append(" := ");
		node.getValue().accept(this);
	}

	@Override
	public void visitBracketedExpression(final BracketedExpression node) {
		append("(");
		descend(node);
		append(")");
	}

	@Override
	public void visitCaseStatement(final CaseStatement node) {
		append("case ");
		node.getAccess().accept(this);
		append(" of ");
		acceptList(node.getItems());
		if (node.getElseCase() != null) {
			node.getElseCase().accept(this);
		}
		append("end");
	}

	@Override
	public void visitClassifierDeclaration(final ClassifierDeclaration node) {

	}

	@Override
	public void visitClassType(final ClassType node) {
		append("class");
		if (node.getAncestorDeclaration() != null) {
			node.getAncestorDeclaration().accept(this);
		}
		indent();
		ensureNewLine();
		for (MemberDeclaration decl : node.getDeclarations()) {
			decl.accept(this);
		}
		undent();
		append("end");
	}

	@Override
	public void visitCodeBlock(final CodeBlock node) {
		ensureNewLine();
		for (Node sect : node.getSections()) {
			if (sect.isProcedural()) {
				indent();
				sect.accept(this);
				undent();
				ensureEmptyLine();
			} else {
				sect.accept(this);
			}
		}
		node.getContent().accept(this);
		append(";");
	}

	@Override
	public void visitCompoundStatement(final CompoundStatement node) {
		ensureNewLine();
		append("begin");
		this.inStatement = false;
		indent();
		ensureNewLine();
		descend(node);
		undent();
		append("end");
	}

	@Override
	public void visitConstDeclaration(final ConstDeclaration node) {
		node.getIdentifier().accept(this);
		if (node.getType() != null) {
			append(": ");
			node.getType().accept(this);
		}
		if (node.getInitializer() != null) {
			append(" = ");
			node.getInitializer().accept(this);
		}
		newStatement();
	}

	@Override
	public void visitDimensionQualifier(final DimensionQualifier node) {
		node.getContent().accept(this);
	}

	@Override
	public void visitExpression(final Expression node) {
		node.getLeftOperand().accept(this);
		if (node.getRightOperand() != null) {
			append(" ");
			append(node.getOperator().getLabel());
			append(" ");
			node.getRightOperand().accept(this);
		}
	}

	@Override
	public void visitFactor(final Factor node) {
		node.getContent().accept(this);
		if (node.getCastType() != null) {
			append(" as ");
			node.getCastType().accept(this);
		}
	}

	@Override
	public void visitFieldDeclaration(final FieldDeclaration node) {
		descend(node);
	}

	@Override
	public void visitFunctionCall(final FunctionCall node) {
		node.getIdentifier().accept(this);
		if (!node.getArguments().isEmpty()) {
			append("(");
			acceptSeparatedList(node.getArguments(), ", ");
			append(")");
		}
	}

	@Override
	public void visitImplementationSection(final ImplementationSection node) {
		ensureEmptyLine();
		append("implementation");
		ensureNewLine();
		descend(node);
		ensureEmptyLine();
		append("end.");
	}

	@Override
	public void visitInterfaceSection(final InterfaceSection node) {
		ensureEmptyLine();
		append("interface");
		ensureNewLine();
		descend(node);
	}

	@Override
	public void visitInterfaceType(final InterfaceType node) {
		append("interface");
		if (node.getAncestorDeclaration() != null) {
			node.getAncestorDeclaration().accept(this);
		}
		indent();
		ensureNewLine();
		if (node.getGuid() != null) {
			node.getGuid().accept(this);
		}
		for (MemberDeclaration member : node.getMembers()) {
			member.accept(this);
		}
		undent();
		append("end");
	}

	@Override
	public void visitLabelledStatement(final LabelledStatement node) {
		undent();
		newStatement();
		indent();
		node.getLabel().accept(this);
		append(": ");
		node.getStatement().accept(this);
	}

	@Override
	public void visitLiteralString(final LiteralString node) {
		String singleQuote = "'";
		String escapedLiteral = singleQuote
				+ node.getValue().replaceAll(singleQuote, singleQuote + singleQuote) + singleQuote;
		append(escapedLiteral);
	}

	@Override
	public void visitMemberConstructorImplementation(final MemberConstructorImplementation node) {
		ensureEmptyLine();
		append("constructor ");
		node.getIdentifier().accept(this);
		writeParameters(node);
		node.getCodeBlock().accept(this);
	}

	@Override
	public void visitMemberDestructorImplementation(final MemberDestructorImplementation node) {
		ensureEmptyLine();
		append("destructor ");
		node.getIdentifier().accept(this);
		writeParameters(node);
		node.getCodeBlock().accept(this);
	}

	@Override
	public void visitMemberFunctionImplementation(final MemberFunctionImplementation node) {
		ensureEmptyLine();
		append("function ");
		node.getIdentifier().accept(this);
		writeParameters(node);
		append(": ");
		node.getReturnType().accept(this);
		node.getCodeBlock().accept(this);
	}

	@Override
	public void visitMemberProcedureImplementation(final MemberProcedureImplementation node) {
		ensureEmptyLine();
		append("procedure ");
		node.getIdentifier().accept(this);
		writeParameters(node);
		node.getCodeBlock().accept(this);
	}

	@Override
	public void visitMemberInvocation(final MemberInvocation node) {
		node.getContent().accept(this);
		if (!node.getQualifiers().isEmpty()) {
			append("[");
			acceptSeparatedList(node.getQualifiers(), ", ");
			append("]");
		}
	}

	@Override
	public void visitParameterDeclaration(final ParameterDeclaration node) {
		if (!ParameterModifierKind.DEFAULT.equals(node.getModifier())) {
			append(node.getModifier().toString().toLowerCase());
			append(" ");
		}
		acceptSeparatedList(node.getIdentifiers(), ", ");
		append(": ");
		node.getType().accept(this);
		if (node.getDefaultValue() != null) {
			append(" = ");
			node.getDefaultValue().accept(this);
		}
	}

	@Override
	public void visitPropertyDeclaration(final PropertyDeclaration node) {
		append("property ");
		node.getIdentifier().accept(this);
		if (node.getAccessDeclaration() != null) {
			node.getAccessDeclaration().accept(this);
		}
		if (node.getReadAccessor() != null) {
			node.getReadAccessor().accept(this);
		}
		if (node.getWriteAccessor() != null) {
			node.getWriteAccessor().accept(this);
		}
		newStatement();
	}

	@Override
	public void visitQualifiedIdentifier(final QualifiedIdentifier node) {
		append(node.getName());
	}

	@Override
	public void visitSignedFactor(final SignedFactor node) {
		if (!node.isPositive()) {
			append("-");
		}
		descend(node);
	}

	@Override
	public void visitSignedNumber(final SignedNumber node) {
		if (!node.isPositive()) {
			append("-");
		}
		descend(node);
	}

	@Override
	public void visitSimpleExpression(final SimpleExpression node) {
		descend(node);
	}

	@Override
	public void visitTerm(final Term node) {
		descend(node);
	}

	@Override
	public void visitTypeDeclaration(final TypeDeclaration node) {
		ensureEmptyLine();
		node.getIdentifier().accept(this);
		append(" = ");
		node.getType().accept(this);
		append(";");
	}

	@Override
	public void visitTypeSection(final TypeSection node) {
		if (node.isOnUnit())
			ensureEmptyLine();
		append("type");
		indent();
		ensureNewLine();
		descend(node);
		undent();
	}

	@Override
	public void visitUnit(final Unit node) {
		append("unit ");
		append(node.getIdentifier().getName());
		append(";");
		ensureNewLine();
		if (node.getInterfaceSection() != null) {
			node.getInterfaceSection().accept(this);
		}
		if (node.getImplementationSection() != null) {
			node.getImplementationSection().accept(this);
		}
		if (node.getBody() != null) {
			node.getBody().accept(this);
		}
	}

	@Override
	public void visitUnsignedReal(final UnsignedReal node) {
		append(node.getValue());
	}

	@Override
	public void visitUsesSection(final UsesSection node) {
		ensureEmptyLine();
		append("uses");
		ensureNewLine();
		indent();
		acceptSeparatedList(node.getImports(), ", ");
		append(";");
		undent();
	}

	@Override
	public void visitVariable(final Variable node) {
		if (node.isAddressOf()) {
			append("@");
		}
		append(node.getIdentifier().getName());
		if (!node.getQualifiers().isEmpty()) {
			append("[");
			acceptSeparatedList(node.getQualifiers(), ", ");
			append("]");
		}
		for (int i = 0; i < node.getNumberOfDereferences(); i++) {
			append("^");
		}
	}

	@Override
	public void visitConstSection(final ConstSection node) {
		if (node.isOnUnit())
			ensureEmptyLine();
		append("const");
		indent();
		ensureNewLine();
		newStatement();
		descend(node);
		undent();
	}

	@Override
	public void visitEmptyStatement(final EmptyStatement node) {
		newStatement();
	}

	@Override
	public void visitForStatement(final ForStatement node) {
		newStatement();
		append("for ");
		node.getVariable().accept(this);
		append(" := ");
		node.getStartExpression().accept(this);
		if (node.isAscending()) {
			append(" to ");
		} else {
			append(" downto ");
		}
		node.getEndExpression().accept(this);
		append(" do ");
	}

	@Override
	public void visitGotoStatement(final GotoStatement node) {
		newStatement();
		append("goto ");
		descend(node);
	}

	@Override
	public void visitIdentifier(final Identifier node) {
		append(node.getName());
	}

	@Override
	public void visitIfStatement(final IfStatement node) {
		newStatement();
		append("if ");
		this.inStatement = false;
		node.getCondition().accept(this);
		append(" then");
		indent();
		ensureNewLine();
		node.getTrueCase().accept(this);
		undent();
		if (node.hasFalseCase()) {
			ensureNewLine();
			append("else");
			ensureNewLine();
			this.inStatement = false;
			indent();
			node.getFalseCase().accept(this);
			undent();
		}
	}

	@Override
	public void visitInitializer(final Initializer node) {
		node.getContents().accept(this);
	}

	@Override
	public void visitLabel(final Label node) {
		node.getContents().accept(this);
	}

	@Override
	public void visitLabelSection(final LabelSection node) {
		append("labels");
		indent();
		ensureNewLine();
		descend(node);
		undent();
	}

	@Override
	public void visitMemberAccess(final MemberAccess node) {
		if (node.isCallInherited()) {
			append("inherited ");
		}
		acceptSeparatedList(node.getInvocations(), ".");
	}

	private void writeParameters(final Parameterized node) {
		if (node.hasParameters()) {
			append("(");
			acceptSeparatedList(node.getParameters(), "; ");
			append(")");
		}
	}

	private void writeMethodModifiers(final MethodDeclaration node) {
		acceptSeparatedList(node.getDirectives(), "; ");
	}

	@Override
	public void visitMemberConstructorDeclaration(final MemberConstructorDeclaration node) {
		writeAccessKind(node);
		append("constructor ");
		node.getIdentifier().accept(this);
		writeParameters(node);
		writeMethodModifiers(node);
		newStatement();
	}

	@Override
	public void visitMemberDestructorDeclaration(final MemberDestructorDeclaration node) {
		writeAccessKind(node);
		append("destructor ");
		node.getIdentifier().accept(this);
		writeParameters(node);
		writeMethodModifiers(node);
		newStatement();
	}

	@Override
	public void visitMemberFunctionDeclaration(final MemberFunctionDeclaration node) {
		writeAccessKind(node);
		append("function ");
		node.getIdentifier().accept(this);
		writeParameters(node);
		append(": ");
		node.getReturnType().accept(this);
		writeMethodModifiers(node);
		newStatement();
	}

	@Override
	public void visitMemberProcedureDeclaration(final MemberProcedureDeclaration node) {
		writeAccessKind(node);
		append("procedure ");
		node.getIdentifier().accept(this);
		writeParameters(node);
		writeMethodModifiers(node);
		newStatement();
	}

	private void writeAccessKind(final MemberDeclaration node) {
		append(node.getAccessKind().toString().toLowerCase());
		append(" ");
	}

	@Override
	public void visitMethodDirective(final MethodDirective node) {
		append("; ");
		append(node.getKind().toString().toLowerCase());
	}

	@Override
	public void visitRaiseExceptionStatement(final RaiseExceptionStatement node) {
		newStatement();
		append("raise");
		if (node.getOptionalExpression() != null) {
			append(" ");
			descend(node);
		}
	}

	@Override
	public void visitRepeatStatement(final RepeatStatement node) {
		newStatement();
		append("repeat");
		ensureNewLine();
		indent();
		this.inStatement = false;
		for (Statement statement : node.getStatements()) {
			statement.accept(this);
		}
		undent();
		append("until ");
		node.getCondition().accept(this);
	}

	@Override
	public void visitSimpleExpressionOperator(final SimpleExpressionOperator node) {
		append(" ");
		append(node.getKind().getLabel());
		append(" ");
	}

	@Override
	public void visitStaticStringType(final StaticStringType node) {
		append("String(");
		descend(node);
		append(")");
	}

	@Override
	public void visitTermOperator(final TermOperator node) {
		append(" ");
		append(node.getKind().getLabel());
		append(" ");
	}

	@Override
	public void visitTryBlock(final TryBlock node) {
		append("try");
		indent();
		ensureNewLine();
		acceptList(node.getStatements());
		undent();
		node.getHandler().accept(this);
	}

	@Override
	public void visitTypeIdentifier(final TypeIdentifier node) {
		append(node.getName());
	}

	@Override
	public void visitUnsignedInteger(final UnsignedInteger node) {
		append(node.getValue());
	}

	@Override
	public void visitVariableDeclaration(final VariableDeclaration node) {
		acceptSeparatedList(node.getIdentifiers(), ", ");
		append(": ");
		if (node.getType() != null) {
			node.getType().accept(this);
		}
		if (node.hasDefaultValue()) {
			append(" = ");
			node.getDefaultValue().accept(this);
		}
		newStatement();
	}

	@Override
	public void visitVarSection(final VarSection node) {
		if (node.isOnUnit())
			ensureEmptyLine();
		append("var");
		indent();
		ensureNewLine();
		descend(node);
		undent();
	}

	@Override
	public void visitWhileStatement(final WhileStatement node) {
		newStatement();
		append("while ");
		node.getExpression().accept(this);
		append(" do ");
		node.getBody().accept(this);
	}

	@Override
	public void visitWithStatement(final WithStatement node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitGuidSpecifier(final GuidSpecifier node) {
		append("[");
		descend(node);
		append("]");
		ensureNewLine();
	}

	@Override
	public void visitArrayInitializer(final ArrayInitializer node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitCaseItem(final CaseItem node) {
		this.inStatement = false;
		ensureNewLine();
		acceptList(node.getConstants());
		append(": ");
		ensureNewLine();
		indent();
		node.getStatement().accept(this);
		undent();
		append(";");
	}

	@Override
	public void visitElseCase(final ElseCase node) {
		this.inStatement = false;
		ensureNewLine();
		append("else");
		ensureNewLine();
		indent();
		acceptList(node.getStatements());
		undent();
	}

	@Override
	public void visitNil(final Nil node) {
		append("nil");
	}

	@Override
	public void visitPointerType(final PointerType node) {
		append("Pointer");
	}

	@Override
	public void visitProcedureImplementation(final ProcedureImplementation node) {
		ensureEmptyLine();
		append("procedure ");
		node.getIdentifier().accept(this);
		writeParameters(node);
		append(";");
		node.getCodeBlock().accept(this);
	}

	@Override
	public void visitFunctionImplementation(final FunctionImplementation node) {
		ensureEmptyLine();
		append("function ");
		node.getIdentifier().accept(this);
		writeParameters(node);
		append(": ");
		node.getReturnType().accept(this);
		append(";");
		node.getCodeBlock().accept(this);
	}

	@Override
	public void visitProcedureType(final ProcedureType node) {
		append("procedure ");
		writeParameters(node);
		if (node.isOfObject()) {
			append(" of object");
		}
	}

	@Override
	public void visitRecordInitializer(final RecordInitializer node) {

	}

	@Override
	public void visitRecordType(final RecordType node) {
		append("record ");
		ensureNewLine();
		descend(node);
		ensureNewLine();
		append("end;");
	}

	@Override
	public void visitScalarDeclaration(final ScalarDeclaration node) {
		node.getIdentifier().accept(this);
		if (node.getValue() != null) {
			append(" = ");
			node.getValue().accept(this);
		}
	}

	@Override
	public void visitSetConstant(final SetConstant node) {
		append("[");
		acceptSeparatedList(node.getElements(), ", ");
		append("]");
	}

	@Override
	public void visitSetType(final SetType node) {
		append("set of ");
		node.getType().accept(this);
	}

	@Override
	public void visitSubrangeType(final SubrangeType node) {
		node.getFirst().accept(this);
		append("..");
		node.getSecond().accept(this);
	}

	@Override
	public void visitTypeCheck(final TypeCheck node) {
		node.getAccess().accept(this);
		append(" is ");
		node.getType().accept(this);
	}

	@Override
	public void visitElement(final Element node) {
		node.getFirst().accept(this);
		if (node.getSecond() != null) {
			append("..");
			node.getSecond().accept(this);
		}
	}

	@Override
	public void visitFinallyClause(final FinallyClause node) {
		ensureNewLine();
		append("finally");
		this.inStatement = false;
		ensureNewLine();
		indent();
		descend(node);
		ensureNewLine();
		undent();
		append("end");
	}

	@Override
	public void visitExceptClause(final ExceptClause node) {
		ensureNewLine();
		append("except");
		this.inStatement = false;
		indent();
		ensureNewLine();
		descend(node);
		ensureNewLine();
		undent();
		append("end");
	}

	@Override
	public void visitRecordField(final RecordField node) {
		node.getIdentifier().accept(this);
		append(": ");
		node.getValue().accept(this);
	}

	@Override
	public void visitRecordSection(final RecordSection node) {
		acceptSeparatedList(node.getIdentifiers(), ", ");
		append(": ");
		node.getType().accept(this);
	}

	@Override
	public void visitScalarType(final ScalarType node) {
		append("(");
		acceptSeparatedList(node.getScalars(), ", ");
		append(")");
	}

	@Override
	public void visitVariantPart(final VariantPart node) {
		append("case ");
		node.getIdentifier().accept(this);
		append(":");
		node.getType().accept(this);
		append(" of ");
		acceptSeparatedList(node.getVariants(), "; ");
	}

	@Override
	public void visitPropertyAccessDeclaration(final PropertyAccessDeclaration node) {
		append("[");
		acceptSeparatedList(node.getDetails(), ", ");
		append("]");
	}

	@Override
	public void visitWriteAccessorDeclaration(final WriteAccessorDeclaration node) {
		append(" write ");
		descend(node);
	}

	@Override
	public void visitReadAccessorDeclaration(final ReadAccessorDeclaration node) {
		append(" read ");
		descend(node);
	}

	@Override
	public void visitVariant(final Variant node) {
		acceptSeparatedList(node.getConstants(), ", ");
		append(": ");
		acceptSeparatedList(node.getFields(), "; ");
	}

	@Override
	public void visitFixedPart(final FixedPart node) {
		acceptSeparatedList(node.getSections(), ";");
	}

	@Override
	public void visitRecordFieldDeclaration(final RecordFieldDeclaration node) {

	}

	@Override
	public void visitMemberAccessStatement(final MemberAccessStatement node) {
		newStatement();
		descend(node);
	}

	@Override
	public void visitBlockComment(final BlockComment node) {
		append(node.getBody());
	}

	@Override
	public void visitLineComment(final LineComment node) {
		append(node.getBody());
	}

	@Override
	public void visitPropertyModifier(final PropertyModifier node) {
		append("; ");
		append(node.getKind().toString().toLowerCase());
	}

	@Override
	public void visitFunctionDirective(final FunctionDirective node) {
		append("; ");
		append(node.getKind().toString().toLowerCase());
	}

	@Override
	public void visitFunctionType(final FunctionType node) {
		append("procedure ");
		writeParameters(node);
		if (node.isOfObject()) {
			append(" of object");
		}
		node.getReturnType().accept(this);
		acceptList(node.getModifiers());
	}

	@Override
	public void visitFunctionDeclaration(final FunctionDeclaration node) {
		ensureEmptyLine();
		append("function ");
		node.getIdentifier().accept(this);
		writeParameters(node);
		append(": ");
		node.getReturnType().accept(this);
		acceptList(node.getModifiers());
		append(";");
		ensureNewLine();
	}

	@Override
	public void visitProcedureDeclaration(final ProcedureDeclaration node) {
		ensureEmptyLine();
		append("procedure ");
		node.getIdentifier().accept(this);
		writeParameters(node);
		acceptList(node.getModifiers());
		append(";");
		ensureNewLine();
	}

	@Override
	public void visitClassOfClassType(final ClassOfClassType node) {
		append("class of ");
		node.getType().accept(this);
	}

	@Override
	public void visitQualifiedTypeIdentifier(final QualifiedTypeIdentifier node) {
		append(node.getNamespaceName());
		append(".");
		append(node.getName());
	}

	@Override
	public void visitPrimitiveFunctionConstant(final PrimitiveFunctionConstant node) {
		node.getCastType().accept(this);
		append("(");
		node.getArgument().accept(this);
		append(")");
	}

	@Override
	public void visitExceptionHandler(final ExceptionHandler node) {
		append("on ");
		node.getIdentifier().accept(this);
		append(": ");
		node.getExceptionType().accept(this);
		append(" do ");
		node.getStatement().accept(this);
	}

	public static String writeToString(final Node node) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			Writer streamWriter = new OutputStreamWriter(bos);
			DelphiSyntaxWriter syntaxWriter = new DelphiSyntaxWriter(streamWriter);
			syntaxWriter.writeNode(node);
			streamWriter.close();
			return bos.toString();
		} catch (IOException e) {
			throw new RuntimeException(String.format("Failed to write node %s to a string.", node));
		}
	}

}
