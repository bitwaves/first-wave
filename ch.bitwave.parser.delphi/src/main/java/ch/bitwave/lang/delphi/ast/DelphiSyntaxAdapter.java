package ch.bitwave.lang.delphi.ast;

import java.util.List;

public abstract class DelphiSyntaxAdapter implements DelphiSyntaxVisitor {

	/**
	 * Visits all details of this node in their natural order.
	 * 
	 * @param node
	 */
	protected void descend(final Node node) {
		for (Node detail : node.getDetails()) {
			detail.accept(this);
		}
	}

	protected void acceptList(final List<? extends Node> list) {
		for (Node node : list) {
			node.accept(this);
		}
	}

	@Override
	public void visitAncestorDeclaration(final AncestorDeclaration node) {
		descend(node);
	}

	@Override
	public void visitArrayInitializer(final ArrayInitializer node) {
		descend(node);
	}

	@Override
	public void visitArrayType(final ArrayType node) {
		descend(node);
	}

	@Override
	public void visitAssignmentStatement(final AssignmentStatement node) {
		descend(node);
	}

	@Override
	public void visitBracketedExpression(final BracketedExpression node) {
		descend(node);
	}

	@Override
	public void visitCaseItem(final CaseItem node) {
		descend(node);
	}

	@Override
	public void visitCaseStatement(final CaseStatement node) {
		descend(node);
	}

	@Override
	public void visitClassifierDeclaration(final ClassifierDeclaration node) {
		descend(node);
	}

	@Override
	public void visitClassType(final ClassType node) {
		descend(node);
	}

	@Override
	public void visitCodeBlock(final CodeBlock node) {
		descend(node);
	}

	@Override
	public void visitCompoundStatement(final CompoundStatement node) {
		descend(node);
	}

	@Override
	public void visitConstDeclaration(final ConstDeclaration node) {
		descend(node);
	}

	@Override
	public void visitConstSection(final ConstSection node) {
		descend(node);
	}

	@Override
	public void visitDimensionQualifier(final DimensionQualifier node) {
		descend(node);
	}

	@Override
	public void visitElseCase(final ElseCase node) {
		descend(node);
	}

	@Override
	public void visitEmptyStatement(final EmptyStatement node) {
		descend(node);
	}

	@Override
	public void visitExpression(final Expression node) {
		descend(node);
	}

	@Override
	public void visitFactor(final Factor node) {
		descend(node);
	}

	@Override
	public void visitFieldDeclaration(final FieldDeclaration node) {
		descend(node);
	}

	@Override
	public void visitForStatement(final ForStatement node) {
		descend(node);
	}

	@Override
	public void visitFunctionCall(final FunctionCall node) {
		descend(node);
	}

	@Override
	public void visitFunctionImplementation(final FunctionImplementation node) {
		descend(node);
	}

	@Override
	public void visitGotoStatement(final GotoStatement node) {
		descend(node);
	}

	@Override
	public void visitGuidSpecifier(final GuidSpecifier node) {
		descend(node);
	}

	@Override
	public void visitIdentifier(final Identifier node) {
		descend(node);
	}

	@Override
	public void visitIfStatement(final IfStatement node) {
		descend(node);
	}

	@Override
	public void visitImplementationSection(final ImplementationSection node) {
		descend(node);
	}

	@Override
	public void visitInterfaceSection(final InterfaceSection node) {
		descend(node);
	}

	@Override
	public void visitInterfaceType(final InterfaceType node) {
		descend(node);
	}

	@Override
	public void visitLabel(final Label node) {
		descend(node);
	}

	@Override
	public void visitLabelledStatement(final LabelledStatement node) {
		descend(node);
	}

	@Override
	public void visitLabelSection(final LabelSection node) {
		descend(node);
	}

	@Override
	public void visitLiteralString(final LiteralString node) {
		descend(node);
	}

	@Override
	public void visitMemberAccess(final MemberAccess node) {
		descend(node);
	}

	@Override
	public void visitMemberConstructorDeclaration(final MemberConstructorDeclaration node) {
		descend(node);
	}

	@Override
	public void visitMemberConstructorImplementation(final MemberConstructorImplementation node) {
		descend(node);
	}

	@Override
	public void visitMemberDestructorDeclaration(final MemberDestructorDeclaration node) {
		descend(node);
	}

	@Override
	public void visitMemberDestructorImplementation(final MemberDestructorImplementation node) {
		descend(node);
	}

	@Override
	public void visitMemberFunctionDeclaration(final MemberFunctionDeclaration node) {
		descend(node);
	}

	@Override
	public void visitMemberFunctionImplementation(final MemberFunctionImplementation node) {
		descend(node);
	}

	@Override
	public void visitMemberInvocation(final MemberInvocation node) {
		descend(node);
	}

	@Override
	public void visitMemberProcedureDeclaration(final MemberProcedureDeclaration node) {
		descend(node);
	}

	@Override
	public void visitMemberProcedureImplementation(final MemberProcedureImplementation node) {
		descend(node);
	}

	@Override
	public void visitMethodDirective(final MethodDirective node) {
		descend(node);
	}

	@Override
	public void visitNil(final Nil node) {
		descend(node);
	}

	@Override
	public void visitParameterDeclaration(final ParameterDeclaration node) {
		descend(node);
	}

	@Override
	public void visitPointerType(final PointerType node) {
		descend(node);
	}

	@Override
	public void visitProcedureImplementation(final ProcedureImplementation node) {
		descend(node);
	}

	@Override
	public void visitProcedureType(final ProcedureType node) {
		descend(node);
	}

	@Override
	public void visitPropertyDeclaration(final PropertyDeclaration node) {
		descend(node);
	}

	@Override
	public void visitQualifiedIdentifier(final QualifiedIdentifier node) {
		descend(node);
	}

	@Override
	public void visitRaiseExceptionStatement(final RaiseExceptionStatement node) {
		descend(node);
	}

	@Override
	public void visitRecordInitializer(final RecordInitializer node) {
		descend(node);
	}

	@Override
	public void visitRecordType(final RecordType node) {
		descend(node);
	}

	@Override
	public void visitRepeatStatement(final RepeatStatement node) {
		descend(node);
	}

	@Override
	public void visitScalarDeclaration(final ScalarDeclaration node) {
		descend(node);
	}

	@Override
	public void visitSetConstant(final SetConstant node) {
		descend(node);
	}

	@Override
	public void visitSetType(final SetType node) {
		descend(node);
	}

	@Override
	public void visitSignedFactor(final SignedFactor node) {
		descend(node);
	}

	@Override
	public void visitSignedNumber(final SignedNumber node) {
		descend(node);
	}

	@Override
	public void visitSimpleExpression(final SimpleExpression node) {
		descend(node);
	}

	@Override
	public void visitSimpleExpressionOperator(final SimpleExpressionOperator node) {
		descend(node);
	}

	@Override
	public void visitStaticStringType(final StaticStringType node) {
		descend(node);
	}

	@Override
	public void visitSubrangeType(final SubrangeType node) {
		descend(node);
	}

	@Override
	public void visitTerm(final Term node) {
		descend(node);
	}

	@Override
	public void visitTermOperator(final TermOperator node) {
		descend(node);
	}

	@Override
	public void visitTryBlock(final TryBlock node) {
		descend(node);
	}

	@Override
	public void visitTypeCheck(final TypeCheck node) {
		descend(node);
	}

	@Override
	public void visitTypeDeclaration(final TypeDeclaration node) {
		descend(node);
	}

	@Override
	public void visitTypeIdentifier(final TypeIdentifier node) {
		descend(node);
	}

	@Override
	public void visitTypeSection(final TypeSection node) {
		descend(node);
	}

	@Override
	public void visitUnit(final Unit node) {
		descend(node);
	}

	@Override
	public void visitUnsignedInteger(final UnsignedInteger node) {
		descend(node);
	}

	@Override
	public void visitUnsignedReal(final UnsignedReal node) {
		descend(node);
	}

	@Override
	public void visitUsesSection(final UsesSection node) {
		descend(node);
	}

	@Override
	public void visitVariable(final Variable node) {
		descend(node);
	}

	@Override
	public void visitVariableDeclaration(final VariableDeclaration node) {
		descend(node);
	}

	@Override
	public void visitVarSection(final VarSection node) {
		descend(node);
	}

	@Override
	public void visitWhileStatement(final WhileStatement node) {
		descend(node);
	}

	@Override
	public void visitWithStatement(final WithStatement node) {
		descend(node);
	}

	@Override
	public void visitInitializer(final Initializer node) {
		descend(node);
	}

	@Override
	public void visitElement(final Element node) {
		descend(node);
	}

	@Override
	public void visitFinallyClause(final FinallyClause node) {
		descend(node);
	}

	@Override
	public void visitRecordField(final RecordField node) {
		descend(node);
	}

	@Override
	public void visitRecordSection(final RecordSection node) {
		descend(node);
	}

	@Override
	public void visitScalarType(final ScalarType node) {
		descend(node);
	}

	@Override
	public void visitVariantPart(final VariantPart node) {
		descend(node);
	}

	@Override
	public void visitPropertyAccessDeclaration(final PropertyAccessDeclaration node) {
		descend(node);
	}

	@Override
	public void visitWriteAccessorDeclaration(final WriteAccessorDeclaration node) {
		descend(node);
	}

	@Override
	public void visitReadAccessorDeclaration(final ReadAccessorDeclaration node) {
		descend(node);
	}

	@Override
	public void visitVariant(final Variant node) {
		descend(node);
	}

	@Override
	public void visitFixedPart(final FixedPart node) {
		descend(node);
	}

	@Override
	public void visitRecordFieldDeclaration(final RecordFieldDeclaration node) {
		descend(node);
	}

	@Override
	public void visitMemberAccessStatement(final MemberAccessStatement node) {
		descend(node);
	}

	@Override
	public void visitBlockComment(final BlockComment node) {
		descend(node);
	}

	@Override
	public void visitLineComment(final LineComment node) {
		descend(node);
	}

	@Override
	public void visitPropertyModifier(final PropertyModifier node) {
		descend(node);
	}

	@Override
	public void visitFunctionDirective(final FunctionDirective node) {
		descend(node);
	}

	@Override
	public void visitFunctionType(final FunctionType node) {
		descend(node);
	}

	@Override
	public void visitExceptClause(final ExceptClause node) {
		descend(node);
	}

	@Override
	public void visitExceptionHandler(final ExceptionHandler node) {
		descend(node);
	}

	@Override
	public void visitRecordItem(final RecordItem node) {
		descend(node);
	}

	@Override
	public void visitFunctionDeclaration(final FunctionDeclaration node) {
		descend(node);
	}

	@Override
	public void visitProcedureDeclaration(final ProcedureDeclaration node) {
		descend(node);
	}

	@Override
	public void visitClassOfClassType(final ClassOfClassType node) {
		descend(node);
	}

	@Override
	public void visitQualifiedTypeIdentifier(final QualifiedTypeIdentifier node) {
		descend(node);
	}

	@Override
	public void visitPrimitiveFunctionConstant(final PrimitiveFunctionConstant node) {
		descend(node);
	}
}
