package ch.bitwave.lang.delphi.mapping;

import java.util.Stack;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

import ch.bitwave.lang.delphi.ast.*;
import ch.bitwave.lang.delphi.parser.*;
import ch.bitwave.lang.delphi.parser.DelphiParser.AbstractDirectiveContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.AncestorDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ArrayInitializerContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ArrayTypeContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.AssignmentStatementContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.BracketedExpressionContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.CaseItemContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.CaseStatementContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.CdeclDirectiveContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ClassMemberDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ClassOfClassTypeContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ClassTypeContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.CodeBlockContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.CompoundStatementContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ConstDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ConstSectionContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.DefaultModifierContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.DelphiUnitContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.DimensionQualifierContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ElementContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ElseCaseContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.EmptyStatementContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ExceptClauseContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ExceptionHandlerContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ExpressionContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ExpressionOperatorContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.FactorContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.FieldDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.FileTypeContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.FinallyClauseContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.FixedPartContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ForStatementContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ForwardDirectiveContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.FunctionCallContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.FunctionDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.FunctionImplementationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.FunctionTypeContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.GotoStatementContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.GuidSpecifierContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.IdentifierContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.IfStatementContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ImplementationSectionContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.InitializerContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.InterfaceSectionContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.InterfaceTypeContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.LabelContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.LabelSectionContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.LiteralStringContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.MemberAccessContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.MemberAccessStatementContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.MemberConstructorDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.MemberConstructorImplementationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.MemberDestructorDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.MemberDestructorImplementationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.MemberFunctionDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.MemberFunctionImplementationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.MemberInvocationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.MemberProcedureDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.MemberProcedureImplementationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.NilContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.OverloadDirectiveContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.OverrideDirectiveContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ParamModifierContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ParameterDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.PointerTypeContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.PrimitiveFunctionConstantContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ProcedureDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ProcedureImplementationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ProcedureTypeContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.PropertyAccessDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.PropertyDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.QualifiedIdentifierContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.QualifiedTypeIdentifierContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.RaiseExceptionContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ReadAccessorDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.RecordFieldContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.RecordFieldDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.RecordInitializerContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.RecordItemContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.RecordSectionContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.RecordTypeContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ReintroduceDirectiveContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.RepeatStatementContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ScalarDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.ScalarTypeContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.SetConstantContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.SetTypeContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.SignedFactorContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.SignedNumberContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.SimpleExpressionContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.SimpleOperatorContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.StaticStringTypeContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.StdcallDirectiveContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.SubrangeTypeContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.TermContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.TermOperatorContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.TryBlockContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.TypeCheckContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.TypeDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.TypeIdentifierContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.TypeSectionContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.UnsignedIntegerContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.UnsignedRealContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.UsesSectionContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.VarSectionContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.VariableContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.VariableDeclarationContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.VariantContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.VariantPartContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.VirtualDirectiveContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.WhileStatementContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.WithStatementContext;
import ch.bitwave.lang.delphi.parser.DelphiParser.WriteAccessorDeclarationContext;

/**
 * Maps a Delphi parse tree into a Delphi AST. Requires the parser to generate
 * the full tree.
 */
public class DelphiSyntaxMapper extends DelphiBaseListener {

	private static final Logger LOGGER = Logger.getLogger(DelphiSyntaxMapper.class.getSimpleName());
	private AccessModifierKind currentMemberAccess;
	private Stack<Node> nodeStack;
	private Unit unit;

	public DelphiSyntaxMapper() {
		this.nodeStack = new Stack<Node>();
	}

	private void addNode(final Node node) {
		Node top = getNode();
		addToNode(top, node);
	}

	private void addToNode(final Node top, final Node node) {
		// Push must happen before add because the AST nodes may except on an
		// invalid add, which leads to the parser closing all rules which in
		// turn requires a healthy stack.
		try {
			top.add(node);
			node.setParent(top);
		} catch (InvalidNodeException e) {
			LOGGER.severe(e.getMessage());
		}
	}

	@Override
	public void enterAbstractDirective(final AbstractDirectiveContext ctx) {
		MethodDirective node = new MethodDirective();
		node.setKind(MethodDirectiveKind.ABSTRACT);
		addNode(node);
	}

	@Override
	public void enterAncestorDeclaration(final AncestorDeclarationContext ctx) {
		pushAndAddNode(new AncestorDeclaration());
	}

	@Override
	public void enterArrayInitializer(final ArrayInitializerContext ctx) {
		pushAndAddNode(new ArrayInitializer());
	}

	@Override
	public void enterArrayType(final ArrayTypeContext ctx) {
		pushAndAddNode(new ArrayType());
	}

	@Override
	public void enterAssignmentStatement(final AssignmentStatementContext ctx) {
		pushAndAddNode(new AssignmentStatement());
	}

	@Override
	public void enterBracketedExpression(final BracketedExpressionContext ctx) {
		pushAndAddNode(new BracketedExpression());
	}

	@Override
	public void enterCaseItem(final CaseItemContext ctx) {
		pushAndAddNode(new CaseItem());
	}

	@Override
	public void enterCaseStatement(final CaseStatementContext ctx) {
		pushAndAddNode(new CaseStatement());
	}

	@Override
	public void enterClassOfClassType(final ClassOfClassTypeContext ctx) {
		pushAndAddNode(new ClassOfClassType());
	}

	@Override
	public void enterClassType(final ClassTypeContext ctx) {
		pushAndAddNode(new ClassType());
		this.currentMemberAccess = AccessModifierKind.DEFAULT;
	}

	@Override
	public void enterCodeBlock(final CodeBlockContext ctx) {
		pushAndAddNode(new CodeBlock());
	}

	@Override
	public void enterCompoundStatement(final CompoundStatementContext ctx) {
		pushAndAddNode(new CompoundStatement());
	}

	@Override
	public void enterConstDeclaration(final ConstDeclarationContext ctx) {
		pushAndAddNode(new ConstDeclaration());
	}

	@Override
	public void enterConstSection(final ConstSectionContext ctx) {
		pushAndAddNode(new ConstSection());
	}

	@Override
	public void enterDefaultModifier(final DefaultModifierContext ctx) {
		PropertyModifier node = new PropertyModifier();
		node.setKind(PropertyModifierKind.DEFAULT);
		addNode(node);
	}

	@Override
	public void enterDelphiUnit(final DelphiUnitContext ctx) {
		this.unit = new Unit();
		pushNode(this.unit);
	}

	@Override
	public void enterDimensionQualifier(final DimensionQualifierContext ctx) {
		pushAndAddNode(new DimensionQualifier());
	}

	@Override
	public void enterElement(final ElementContext ctx) {
		pushAndAddNode(new Element());
	}

	@Override
	public void enterElseCase(final ElseCaseContext ctx) {
		pushAndAddNode(new ElseCase());
	}

	@Override
	public void enterExceptClause(final ExceptClauseContext ctx) {
		pushAndAddNode(new ExceptClause());
	}

	@Override
	public void enterExceptionHandler(final ExceptionHandlerContext ctx) {
		pushAndAddNode(new ExceptionHandler());
	}

	@Override
	public void enterExpression(final ExpressionContext ctx) {
		pushAndAddNode(new Expression());
	}

	@Override
	public void enterFactor(final FactorContext ctx) {
		pushAndAddNode(new Factor());
	}

	@Override
	public void enterFieldDeclaration(final FieldDeclarationContext ctx) {
		pushAndAddNode(new FieldDeclaration(this.currentMemberAccess));
	}

	@Override
	public void enterFileType(final FileTypeContext ctx) {

		super.enterFileType(ctx);
	}

	@Override
	public void enterFinallyClause(final FinallyClauseContext ctx) {
		pushAndAddNode(new FinallyClause());
	}

	@Override
	public void enterFixedPart(final FixedPartContext ctx) {
		pushAndAddNode(new FixedPart());
	}

	@Override
	public void enterForStatement(final ForStatementContext ctx) {
		pushAndAddNode(new ForStatement());
	}

	@Override
	public void enterFunctionCall(final FunctionCallContext ctx) {
		pushAndAddNode(new FunctionCall());
	}

	@Override
	public void enterFunctionDeclaration(final FunctionDeclarationContext ctx) {
		pushAndAddNode(new FunctionDeclaration());
	}

	@Override
	public void enterFunctionImplementation(final FunctionImplementationContext ctx) {
		pushAndAddNode(new FunctionImplementation());
	}

	@Override
	public void enterFunctionType(final FunctionTypeContext ctx) {
		pushAndAddNode(new FunctionType());
	}

	@Override
	public void enterGotoStatement(final GotoStatementContext ctx) {
		pushAndAddNode(new GotoStatement());
	}

	@Override
	public void enterGuidSpecifier(final GuidSpecifierContext ctx) {
		pushAndAddNode(new GuidSpecifier());
	}

	@Override
	public void enterIfStatement(final IfStatementContext ctx) {
		pushAndAddNode(new IfStatement());
	}

	@Override
	public void enterImplementationSection(final ImplementationSectionContext ctx) {
		ImplementationSection node = new ImplementationSection();
		this.unit.setImplementationSection(node);
		pushNode(node);
	}

	@Override
	public void enterInitializer(final InitializerContext ctx) {
		pushAndAddNode(new Initializer());
	}

	@Override
	public void enterInterfaceSection(final InterfaceSectionContext ctx) {
		InterfaceSection node = new InterfaceSection();
		this.unit.setInterfaceSection(node);
		pushNode(node);
	}

	@Override
	public void enterInterfaceType(final InterfaceTypeContext ctx) {
		pushAndAddNode(new InterfaceType());
		this.currentMemberAccess = AccessModifierKind.PUBLIC;
	}

	@Override
	public void enterLabel(final LabelContext ctx) {
		pushAndAddNode(new Label());
	}

	@Override
	public void enterLabelSection(final LabelSectionContext ctx) {
		pushAndAddNode(new LabelSection());
	}

	@Override
	public void enterMemberAccess(final MemberAccessContext ctx) {
		pushAndAddNode(new MemberAccess());
	}

	@Override
	public void enterMemberAccessStatement(final MemberAccessStatementContext ctx) {
		pushAndAddNode(new MemberAccessStatement());
	}

	@Override
	public void enterMemberConstructorDeclaration(final MemberConstructorDeclarationContext ctx) {
		pushAndAddNode(new MemberConstructorDeclaration(this.currentMemberAccess));
	}

	@Override
	public void enterMemberConstructorImplementation(
			final MemberConstructorImplementationContext ctx) {
		pushAndAddNode(new MemberConstructorImplementation());
	}

	@Override
	public void enterMemberDestructorDeclaration(final MemberDestructorDeclarationContext ctx) {
		pushAndAddNode(new MemberDestructorDeclaration(this.currentMemberAccess));
	}

	@Override
	public void enterMemberDestructorImplementation(final MemberDestructorImplementationContext ctx) {
		pushAndAddNode(new MemberDestructorImplementation());
	}

	@Override
	public void enterMemberFunctionDeclaration(final MemberFunctionDeclarationContext ctx) {
		pushAndAddNode(new MemberFunctionDeclaration(this.currentMemberAccess));
	}

	@Override
	public void enterMemberFunctionImplementation(final MemberFunctionImplementationContext ctx) {
		pushAndAddNode(new MemberFunctionImplementation());
	}

	@Override
	public void enterMemberInvocation(final MemberInvocationContext ctx) {
		pushAndAddNode(new MemberInvocation());
	}

	@Override
	public void enterMemberProcedureDeclaration(final MemberProcedureDeclarationContext ctx) {
		pushAndAddNode(new MemberProcedureDeclaration(this.currentMemberAccess));
	}

	@Override
	public void enterMemberProcedureImplementation(final MemberProcedureImplementationContext ctx) {
		pushAndAddNode(new MemberProcedureImplementation());
	}

	@Override
	public void enterOverrideDirective(final OverrideDirectiveContext ctx) {
		MethodDirective node = new MethodDirective();
		node.setKind(MethodDirectiveKind.OVERRIDE);
		addNode(node);
	}

	@Override
	public void enterParameterDeclaration(final ParameterDeclarationContext ctx) {
		pushAndAddNode(new ParameterDeclaration());
	}

	@Override
	public void enterPointerType(final PointerTypeContext ctx) {
		pushAndAddNode(new PointerType());
	}

	@Override
	public void enterPrimitiveFunctionConstant(final PrimitiveFunctionConstantContext ctx) {
		pushAndAddNode(new PrimitiveFunctionConstant());
	}

	@Override
	public void enterProcedureDeclaration(final ProcedureDeclarationContext ctx) {
		pushAndAddNode(new ProcedureDeclaration());
	}

	@Override
	public void enterProcedureImplementation(final ProcedureImplementationContext ctx) {
		pushAndAddNode(new ProcedureImplementation());
	}

	@Override
	public void enterProcedureType(final ProcedureTypeContext ctx) {
		pushAndAddNode(new ProcedureType());
	}

	@Override
	public void enterPropertyAccessDeclaration(final PropertyAccessDeclarationContext ctx) {
		pushAndAddNode(new PropertyAccessDeclaration());
	}

	@Override
	public void enterPropertyDeclaration(final PropertyDeclarationContext ctx) {
		pushAndAddNode(new PropertyDeclaration(this.currentMemberAccess));
	}

	@Override
	public void enterQualifiedIdentifier(final QualifiedIdentifierContext ctx) {
		pushAndAddNode(new QualifiedIdentifier());
	}

	@Override
	public void enterRaiseException(final RaiseExceptionContext ctx) {
		pushAndAddNode(new RaiseExceptionStatement());
	}

	@Override
	public void enterReadAccessorDeclaration(final ReadAccessorDeclarationContext ctx) {
		pushAndAddNode(new ReadAccessorDeclaration());
	}

	@Override
	public void enterRecordField(final RecordFieldContext ctx) {
		pushAndAddNode(new RecordField());
	}

	@Override
	public void enterRecordFieldDeclaration(final RecordFieldDeclarationContext ctx) {
		pushAndAddNode(new RecordFieldDeclaration());
	}

	@Override
	public void enterRecordInitializer(final RecordInitializerContext ctx) {
		pushAndAddNode(new RecordInitializer());
	}

	@Override
	public void enterRecordItem(final RecordItemContext ctx) {
		pushAndAddNode(new RecordItem());
	}

	@Override
	public void enterRecordSection(final RecordSectionContext ctx) {
		pushAndAddNode(new RecordSection());
	}

	@Override
	public void enterRecordType(final RecordTypeContext ctx) {
		pushAndAddNode(new RecordType());
	}

	@Override
	public void enterReintroduceDirective(final ReintroduceDirectiveContext ctx) {
		MethodDirective node = new MethodDirective();
		node.setKind(MethodDirectiveKind.REINTRODUCE);
		addNode(node);
	}

	@Override
	public void enterRepeatStatement(final RepeatStatementContext ctx) {
		pushAndAddNode(new RepeatStatement());
	}

	@Override
	public void enterScalarDeclaration(final ScalarDeclarationContext ctx) {
		pushAndAddNode(new ScalarDeclaration());
	}

	@Override
	public void enterScalarType(final ScalarTypeContext ctx) {
		pushAndAddNode(new ScalarType());
	}

	@Override
	public void enterSetConstant(final SetConstantContext ctx) {
		pushAndAddNode(new SetConstant());
	}

	@Override
	public void enterSetType(final SetTypeContext ctx) {
		pushAndAddNode(new SetType());
	}

	@Override
	public void enterSignedFactor(final SignedFactorContext ctx) {
		pushAndAddNode(new SignedFactor());
	}

	@Override
	public void enterSignedNumber(final SignedNumberContext ctx) {
		pushAndAddNode(new SignedNumber());
	}

	@Override
	public void enterSimpleExpression(final SimpleExpressionContext ctx) {
		pushAndAddNode(new SimpleExpression());
	}

	@Override
	public void enterSimpleOperator(final SimpleOperatorContext ctx) {
		pushAndAddNode(new SimpleExpressionOperator());
	}

	@Override
	public void enterStaticStringType(final StaticStringTypeContext ctx) {
		pushAndAddNode(new StaticStringType());
	}

	@Override
	public void enterSubrangeType(final SubrangeTypeContext ctx) {
		pushAndAddNode(new SubrangeType());
	}

	@Override
	public void enterTerm(final TermContext ctx) {
		pushAndAddNode(new Term());
	}

	@Override
	public void enterTermOperator(final TermOperatorContext ctx) {
		pushAndAddNode(new TermOperator());
	}

	@Override
	public void enterTryBlock(final TryBlockContext ctx) {
		pushAndAddNode(new TryBlock());
	}

	@Override
	public void enterTypeCheck(final TypeCheckContext ctx) {
		pushAndAddNode(new TypeCheck());
	}

	@Override
	public void enterTypeDeclaration(final TypeDeclarationContext ctx) {
		pushAndAddNode(new TypeDeclaration());
	}

	@Override
	public void enterTypeSection(final TypeSectionContext ctx) {
		pushAndAddNode(new TypeSection());
	}

	@Override
	public void enterUsesSection(final UsesSectionContext ctx) {
		pushAndAddNode(new UsesSection());
	}

	@Override
	public void enterVariable(final VariableContext ctx) {
		pushAndAddNode(new Variable());
	}

	@Override
	public void enterVariableDeclaration(final VariableDeclarationContext ctx) {
		pushAndAddNode(new VariableDeclaration());
	}

	@Override
	public void enterVariant(final VariantContext ctx) {
		pushAndAddNode(new Variant());
	}

	@Override
	public void enterVariantPart(final VariantPartContext ctx) {
		pushAndAddNode(new VariantPart());
	}

	@Override
	public void enterVarSection(final VarSectionContext ctx) {
		pushAndAddNode(new VarSection());
	}

	@Override
	public void enterVirtualDirective(final VirtualDirectiveContext ctx) {
		MethodDirective node = new MethodDirective();
		node.setKind(MethodDirectiveKind.VIRTUAL);
		addNode(node);
	}

	@Override
	public void enterWhileStatement(final WhileStatementContext ctx) {
		pushAndAddNode(new WhileStatement());
	}

	@Override
	public void enterWithStatement(final WithStatementContext ctx) {
		pushAndAddNode(new WhileStatement());
	}

	@Override
	public void enterWriteAccessorDeclaration(final WriteAccessorDeclarationContext ctx) {
		pushAndAddNode(new WriteAccessorDeclaration());
	}

	@Override
	public void exitAncestorDeclaration(final AncestorDeclarationContext ctx) {
		popNodeAs(AncestorDeclaration.class);
	}

	@Override
	public void exitArrayInitializer(final ArrayInitializerContext ctx) {
		popNodeAs(ArrayInitializer.class);
	}

	@Override
	public void exitArrayType(final ArrayTypeContext ctx) {
		popNodeAs(ArrayType.class);
	}

	@Override
	public void exitAssignmentStatement(final AssignmentStatementContext ctx) {
		popNodeAs(AssignmentStatement.class);
	}

	@Override
	public void exitBracketedExpression(final BracketedExpressionContext ctx) {
		popNodeAs(BracketedExpression.class);
	}

	@Override
	public void exitCaseItem(final CaseItemContext ctx) {
		popNodeAs(CaseItem.class);
	}

	@Override
	public void exitCaseStatement(final CaseStatementContext ctx) {
		popNodeAs(CaseStatement.class);
	}

	@Override
	public void exitCdeclDirective(final CdeclDirectiveContext ctx) {
		FunctionDirective modifier = new FunctionDirective();
		modifier.setKind(FunctionDirectiveKind.CDECL);
		addNode(modifier);
	}

	@Override
	public void exitClassMemberDeclaration(final ClassMemberDeclarationContext ctx) {
		if (ctx.PRIVATE() != null) {
			this.currentMemberAccess = AccessModifierKind.PRIVATE;
		} else if (ctx.PROTECTED() != null) {
			this.currentMemberAccess = AccessModifierKind.PROTECTED;
		} else if (ctx.PUBLIC() != null) {
			this.currentMemberAccess = AccessModifierKind.PUBLIC;
		} else if (ctx.PUBLISHED() != null) {
			this.currentMemberAccess = AccessModifierKind.PUBLISHED;
		}
	}

	@Override
	public void exitClassOfClassType(final ClassOfClassTypeContext ctx) {
		popNodeAs(ClassOfClassType.class);
	}

	@Override
	public void exitClassType(final ClassTypeContext ctx) {
		popNodeAs(ClassType.class);
	}

	@Override
	public void exitCodeBlock(final CodeBlockContext ctx) {
		popNodeAs(CodeBlock.class);
	}

	@Override
	public void exitCompoundStatement(final CompoundStatementContext ctx) {
		popNodeAs(CompoundStatement.class);
	}

	@Override
	public void exitConstDeclaration(final ConstDeclarationContext ctx) {
		popNodeAs(ConstDeclaration.class);
	}

	@Override
	public void exitConstSection(final ConstSectionContext ctx) {
		popNodeAs(ConstSection.class);
	}

	@Override
	public void exitDefaultModifier(final DefaultModifierContext ctx) {
		PropertyModifier modifier = new PropertyModifier();
		modifier.setKind(PropertyModifierKind.DEFAULT);
		addNode(modifier);
	}

	@Override
	public void exitDelphiUnit(final DelphiUnitContext ctx) {
		popNodeAs(Unit.class);
	}

	@Override
	public void exitDimensionQualifier(final DimensionQualifierContext ctx) {
		popNodeAs(DimensionQualifier.class);
	}

	@Override
	public void exitElement(final ElementContext ctx) {
		popNodeAs(Element.class);
	}

	@Override
	public void exitElseCase(final ElseCaseContext ctx) {
		popNodeAs(ElseCase.class);
	}

	@Override
	public void exitEmptyStatement(final EmptyStatementContext ctx) {
		addNode(new EmptyStatement());
	}

	@Override
	public void exitExceptClause(final ExceptClauseContext ctx) {
		popNodeAs(ExceptClause.class);
	}

	@Override
	public void exitExceptionHandler(final ExceptionHandlerContext ctx) {
		popNodeAs(ExceptionHandler.class);
	}

	@Override
	public void exitExpression(final ExpressionContext ctx) {
		popNodeAs(Expression.class);
	}

	@Override
	public void exitExpressionOperator(final ExpressionOperatorContext ctx) {
		Expression node = getNodeAs(Expression.class);
		if (ctx.EQ() != null) {
			node.setOperator(ExpressionOperatorKind.EQUAL);
		} else if (ctx.NEQ() != null) {
			node.setOperator(ExpressionOperatorKind.NOT_EQUAL);
		} else if (ctx.LT() != null) {
			node.setOperator(ExpressionOperatorKind.LESS_THAN);
		} else if (ctx.LTE() != null) {
			node.setOperator(ExpressionOperatorKind.LESS_EQUAL);
		} else if (ctx.GTE() != null) {
			node.setOperator(ExpressionOperatorKind.GRATER_EQUAL);
		} else if (ctx.GT() != null) {
			node.setOperator(ExpressionOperatorKind.GREATER_THAN);
		} else if (ctx.IN() != null) {
			node.setOperator(ExpressionOperatorKind.IN);
		}
	}

	@Override
	public void exitFactor(final FactorContext ctx) {
		Factor node = popNodeAs(Factor.class);
		if (ctx.typeIdentifier() != null) {
			TypeIdentifier ident = new TypeIdentifier();
			ident.setName(ctx.typeIdentifier().getText());
			node.setCastType(ident);
		}
	}

	@Override
	public void exitFieldDeclaration(final FieldDeclarationContext ctx) {
		popNodeAs(FieldDeclaration.class);
	}

	@Override
	public void exitFileType(final FileTypeContext ctx) {

		super.exitFileType(ctx);
	}

	@Override
	public void exitFinallyClause(final FinallyClauseContext ctx) {
		popNodeAs(FinallyClause.class);
	}

	@Override
	public void exitFixedPart(final FixedPartContext ctx) {
		popNodeAs(FixedPart.class);
	}

	@Override
	public void exitForStatement(final ForStatementContext ctx) {
		popNodeAs(ForStatement.class);
	}

	@Override
	public void exitForwardDirective(final ForwardDirectiveContext ctx) {
		FunctionDirective modifier = new FunctionDirective();
		modifier.setKind(FunctionDirectiveKind.FORWARD);
		addNode(modifier);
	}

	@Override
	public void exitFunctionCall(final FunctionCallContext ctx) {
		popNodeAs(FunctionCall.class);
	}

	@Override
	public void exitFunctionDeclaration(final FunctionDeclarationContext ctx) {
		popNodeAs(FunctionDeclaration.class);
	}

	@Override
	public void exitFunctionImplementation(final FunctionImplementationContext ctx) {
		popNodeAs(FunctionImplementation.class);
	}

	@Override
	public void exitFunctionType(final FunctionTypeContext ctx) {
		popNodeAs(FunctionType.class);
	}

	@Override
	public void exitGotoStatement(final GotoStatementContext ctx) {
		popNodeAs(GotoStatement.class);
	}

	@Override
	public void exitGuidSpecifier(final GuidSpecifierContext ctx) {
		popNodeAs(GuidSpecifier.class);
	}

	@Override
	public void exitIdentifier(final IdentifierContext ctx) {
		Identifier ident = new Identifier();
		String text = "";
		if (ctx.IDENT() != null) {
			text = ctx.IDENT().getText();
		} else if (ctx.IDENT() != null) {
			text = ctx.IDENT().getText();
		}
		ident.setName(text);
		addNode(ident);
	}

	@Override
	public void exitIfStatement(final IfStatementContext ctx) {
		popNodeAs(IfStatement.class);
	}

	@Override
	public void exitImplementationSection(final ImplementationSectionContext ctx) {
		popNodeAs(ImplementationSection.class);
	}

	@Override
	public void exitInitializer(final InitializerContext ctx) {
		popNodeAs(Initializer.class);
	}

	@Override
	public void exitInterfaceSection(final InterfaceSectionContext ctx) {
		popNodeAs(InterfaceSection.class);
	}

	@Override
	public void exitInterfaceType(final InterfaceTypeContext ctx) {
		popNodeAs(InterfaceType.class);
	}

	@Override
	public void exitLabel(final LabelContext ctx) {
		popNodeAs(Label.class);
	}

	@Override
	public void exitLabelSection(final LabelSectionContext ctx) {
		popNodeAs(LabelSection.class);
	}

	@Override
	public void exitLiteralString(final LiteralStringContext ctx) {
		LiteralString node = new LiteralString();
		String rawText = ctx.LITERAL_STRING().getText();
		String value = unescapeLiteralString(rawText);
		node.setValue(value);
		addNode(node);
	}

	@Override
	public void exitMemberAccess(final MemberAccessContext ctx) {
		popNodeAs(MemberAccess.class);
	}

	@Override
	public void exitMemberAccessStatement(final MemberAccessStatementContext ctx) {
		popNodeAs(MemberAccessStatement.class);
	}

	@Override
	public void exitMemberConstructorDeclaration(final MemberConstructorDeclarationContext ctx) {
		popNodeAs(MemberConstructorDeclaration.class);
	}

	@Override
	public void exitMemberConstructorImplementation(final MemberConstructorImplementationContext ctx) {
		popNodeAs(InvokableImplementation.class);
	}

	@Override
	public void exitMemberDestructorDeclaration(final MemberDestructorDeclarationContext ctx) {
		popNodeAs(MemberDestructorDeclaration.class);
	}

	@Override
	public void exitMemberDestructorImplementation(final MemberDestructorImplementationContext ctx) {
		popNodeAs(MemberDestructorImplementation.class);
	}

	@Override
	public void exitMemberFunctionDeclaration(final MemberFunctionDeclarationContext ctx) {
		popNodeAs(MemberFunctionDeclaration.class);
	}

	@Override
	public void exitMemberFunctionImplementation(final MemberFunctionImplementationContext ctx) {
		popNodeAs(MemberFunctionImplementation.class);
	}

	@Override
	public void exitMemberInvocation(final MemberInvocationContext ctx) {
		MemberInvocation node = popNodeAs(MemberInvocation.class);
		node.setDereference(ctx.POINTER() != null);
	}

	@Override
	public void exitMemberProcedureDeclaration(final MemberProcedureDeclarationContext ctx) {
		popNodeAs(MemberProcedureDeclaration.class);
	}

	@Override
	public void exitMemberProcedureImplementation(final MemberProcedureImplementationContext ctx) {
		popNodeAs(MemberProcedureImplementation.class);
	}

	@Override
	public void exitNil(final NilContext ctx) {
		addNode(new Nil());
	}

	@Override
	public void exitOverloadDirective(final OverloadDirectiveContext ctx) {
		Node node = getNode();
		if (node instanceof ProceduralDeclaration) {
			FunctionDirective modifier = new FunctionDirective();
			modifier.setKind(FunctionDirectiveKind.OVERLOAD);
			addNode(modifier);
		} else {
			MethodDirective modifier = new MethodDirective();
			modifier.setKind(MethodDirectiveKind.OVERLOAD);
			addNode(modifier);
		}
		super.exitOverloadDirective(ctx);
	}

	@Override
	public void exitParameterDeclaration(final ParameterDeclarationContext ctx) {
		popNodeAs(ParameterDeclaration.class);
	}

	@Override
	public void exitParamModifier(final ParamModifierContext ctx) {
		ParameterDeclaration decl = getNodeAs(ParameterDeclaration.class);
		if (ctx.CONST() != null) {
			decl.setModifier(ParameterModifierKind.CONST);
		} else if (ctx.VAR() != null) {
			decl.setModifier(ParameterModifierKind.VAR);
		} else if (ctx.OUT() != null) {
			decl.setModifier(ParameterModifierKind.OUT);
		}
	}

	@Override
	public void exitPointerType(final PointerTypeContext ctx) {
		popNodeAs(PointerType.class);
	}

	@Override
	public void exitPrimitiveFunctionConstant(final PrimitiveFunctionConstantContext ctx) {
		popNodeAs(PrimitiveFunctionConstant.class);
	}

	@Override
	public void exitProcedureDeclaration(final ProcedureDeclarationContext ctx) {
		popNodeAs(ProcedureDeclaration.class);
	}

	@Override
	public void exitProcedureImplementation(final ProcedureImplementationContext ctx) {
		popNodeAs(ProcedureImplementation.class);
	}

	@Override
	public void exitProcedureType(final ProcedureTypeContext ctx) {
		ProcedureType node = popNodeAs(ProcedureType.class);
		node.setOfObject(ctx.OF() != null);
	}

	@Override
	public void exitPropertyAccessDeclaration(final PropertyAccessDeclarationContext ctx) {
		popNodeAs(PropertyAccessDeclaration.class);
	}

	@Override
	public void exitPropertyDeclaration(final PropertyDeclarationContext ctx) {
		popNodeAs(PropertyDeclaration.class);
	}

	@Override
	public void exitQualifiedIdentifier(final QualifiedIdentifierContext ctx) {
		popNodeAs(QualifiedIdentifier.class);
	}

	@Override
	public void exitQualifiedTypeIdentifier(final QualifiedTypeIdentifierContext ctx) {
		QualifiedTypeIdentifier node = new QualifiedTypeIdentifier();
		node.setNamespaceName(ctx.IDENT(0).getText());
		node.setName(ctx.IDENT(1).getText());
		addNode(node);
	}

	@Override
	public void exitRaiseException(final RaiseExceptionContext ctx) {
		popNodeAs(RaiseExceptionStatement.class);
	}

	@Override
	public void exitReadAccessorDeclaration(final ReadAccessorDeclarationContext ctx) {
		popNodeAs(ReadAccessorDeclaration.class);
	}

	@Override
	public void exitRecordField(final RecordFieldContext ctx) {
		popNodeAs(RecordField.class);
	}

	@Override
	public void exitRecordFieldDeclaration(final RecordFieldDeclarationContext ctx) {
		popNodeAs(RecordFieldDeclaration.class);
	}

	@Override
	public void exitRecordInitializer(final RecordInitializerContext ctx) {
		popNodeAs(RecordInitializer.class);
	}

	@Override
	public void exitRecordItem(final RecordItemContext ctx) {
		popNodeAs(RecordItem.class);
	}

	@Override
	public void exitRecordSection(final RecordSectionContext ctx) {
		popNodeAs(RecordSection.class);
	}

	@Override
	public void exitRecordType(final RecordTypeContext ctx) {
		popNodeAs(RecordType.class);
	}

	@Override
	public void exitRepeatStatement(final RepeatStatementContext ctx) {
		popNodeAs(RepeatStatement.class);
	}

	@Override
	public void exitScalarDeclaration(final ScalarDeclarationContext ctx) {
		popNodeAs(ScalarDeclaration.class);
	}

	@Override
	public void exitScalarType(final ScalarTypeContext ctx) {
		popNodeAs(ScalarType.class);
	}

	@Override
	public void exitSetConstant(final SetConstantContext ctx) {
		popNodeAs(SetConstant.class);
	}

	@Override
	public void exitSetType(final SetTypeContext ctx) {
		popNodeAs(SetType.class);
	}

	@Override
	public void exitSignedFactor(final SignedFactorContext ctx) {
		SignedFactor node = popNodeAs(SignedFactor.class);
		node.setPositive(ctx.sign() == null || ctx.sign().PLUS() != null);
	}

	@Override
	public void exitSignedNumber(final SignedNumberContext ctx) {
		SignedNumber node = popNodeAs(SignedNumber.class);
		node.setPositive(ctx.sign().PLUS() != null);
	}

	@Override
	public void exitSimpleExpression(final SimpleExpressionContext ctx) {
		popNodeAs(SimpleExpression.class);
	}

	@Override
	public void exitSimpleOperator(final SimpleOperatorContext ctx) {
		SimpleExpressionOperator node = popNodeAs(SimpleExpressionOperator.class);
		if (ctx.PLUS() != null) {
			node.setKind(SimpleExpressionOperatorKind.PLUS);
		} else if (ctx.MINUS() != null) {
			node.setKind(SimpleExpressionOperatorKind.MINUS);
		} else if (ctx.OR() != null) {
			node.setKind(SimpleExpressionOperatorKind.OR);
		} else if (ctx.XOR() != null) {
			node.setKind(SimpleExpressionOperatorKind.XOR);
		}
	}

	@Override
	public void exitStaticStringType(final StaticStringTypeContext ctx) {
		popNodeAs(StaticStringType.class);
	}

	@Override
	public void exitStdcallDirective(final StdcallDirectiveContext ctx) {
		FunctionDirective modifier = new FunctionDirective();
		modifier.setKind(FunctionDirectiveKind.STDCALL);
		addNode(modifier);
	}

	@Override
	public void exitSubrangeType(final SubrangeTypeContext ctx) {
		popNodeAs(SubrangeType.class);
	}

	@Override
	public void exitTerm(final TermContext ctx) {
		popNodeAs(Term.class);
	}

	@Override
	public void exitTermOperator(final TermOperatorContext ctx) {
		TermOperator node = popNodeAs(TermOperator.class);
		if (ctx.STAR() != null) {
			node.setKind(TermOperatorKind.MULT);
		} else if (ctx.DIV() != null) {
			node.setKind(TermOperatorKind.DIV_INTEGER);
		} else if (ctx.SLASH() != null) {
			node.setKind(TermOperatorKind.DIV_REAL);
		} else if (ctx.MOD() != null) {
			node.setKind(TermOperatorKind.MODULO);
		} else if (ctx.AND() != null) {
			node.setKind(TermOperatorKind.AND);
		} else if (ctx.SHL() != null) {
			node.setKind(TermOperatorKind.SHFT_LEFT);
		} else if (ctx.SHR() != null) {
			node.setKind(TermOperatorKind.SHIFT_RIGHT);
		}
	}

	@Override
	public void exitTryBlock(final TryBlockContext ctx) {
		popNodeAs(TryBlock.class);
	}

	@Override
	public void exitTypeCheck(final TypeCheckContext ctx) {
		popNodeAs(TypeCheck.class);
	}

	@Override
	public void exitTypeDeclaration(final TypeDeclarationContext ctx) {
		popNodeAs(TypeDeclaration.class);
	}

	@Override
	public void exitTypeIdentifier(final TypeIdentifierContext ctx) {
		TypeIdentifier node = new TypeIdentifier();
		String text = null;
		if (ctx.IDENT() != null) {
			text = ctx.IDENT().getText();
		} else if (ctx.CHR() != null) {
			text = ctx.CHR().getText();
		} else if (ctx.CHAR() != null) {
			text = ctx.CHAR().getText();
		} else if (ctx.BOOLEAN() != null) {
			text = ctx.BOOLEAN().getText();
		} else if (ctx.INTEGER() != null) {
			text = ctx.INTEGER().getText();
		} else if (ctx.CARDINAL() != null) {
			text = ctx.CARDINAL().getText();
		} else if (ctx.REAL() != null) {
			text = ctx.REAL().getText();
		} else if (ctx.DOUBLE() != null) {
			text = ctx.DOUBLE().getText();
		} else if (ctx.STRING() != null) {
			text = ctx.STRING().getText();
		}
		node.setName(text);
		addNode(node);
	}

	@Override
	public void exitTypeSection(final TypeSectionContext ctx) {
		popNodeAs(TypeSection.class);
	}

	@Override
	public void exitUnsignedInteger(final UnsignedIntegerContext ctx) {
		UnsignedInteger node = new UnsignedInteger();
		String text = "";
		if (ctx.HEX_LITERAL() != null) {
			text = ctx.HEX_LITERAL().getText();
		} else if (ctx.LITERAL_INTEGER() != null) {
			text = ctx.LITERAL_INTEGER().getText();
		}
		node.setValue(text);
		addNode(node);
	}

	@Override
	public void exitUnsignedReal(final UnsignedRealContext ctx) {
		UnsignedReal node = new UnsignedReal();
		node.setValue(ctx.LITERAL_REAL().getText());
		addNode(node);
	}

	@Override
	public void exitUsesSection(final UsesSectionContext ctx) {
		popNodeAs(UsesSection.class);
	}

	@Override
	public void exitVariable(final VariableContext ctx) {
		Variable node = popNodeAs(Variable.class);
		node.setAddressOf(ctx.AT() != null);
		node.setNumberOfDereferences(ctx.POINTER().size());
	}

	@Override
	public void exitVariableDeclaration(final VariableDeclarationContext ctx) {
		popNodeAs(VariableDeclaration.class);
	}

	@Override
	public void exitVariant(final VariantContext ctx) {
		popNodeAs(Variant.class);
	}

	@Override
	public void exitVariantPart(final VariantPartContext ctx) {
		popNodeAs(VariantPart.class);
	}

	@Override
	public void exitVarSection(final VarSectionContext ctx) {
		popNodeAs(VarSection.class);
	}

	@Override
	public void exitWhileStatement(final WhileStatementContext ctx) {
		popNodeAs(WhileStatement.class);
	}

	@Override
	public void exitWithStatement(final WithStatementContext ctx) {
		popNodeAs(WhileStatement.class);
	}

	@Override
	public void exitWriteAccessorDeclaration(final WriteAccessorDeclarationContext ctx) {
		popNodeAs(WriteAccessorDeclaration.class);
	}

	private Node getNode() {
		if (this.nodeStack.isEmpty()) {
			throw new RuntimeException("Cannot get the current node from an empty stack.");
		}
		return this.nodeStack.get(this.nodeStack.size() - 1);
	}

	@SuppressWarnings("unchecked")
	private <T> T getNodeAs(final Class<T> desiredClass) {
		Node result = getNode();
		if (!desiredClass.isAssignableFrom(result.getClass())) {
			throw new RuntimeException(String.format(
					"Wrong node type on node stack: expected %s but popped %s",
					desiredClass.getSimpleName(), result.getClass().getSimpleName()));
		}
		return (T) result;
	}

	public Unit getUnit() {
		return this.unit;
	}

	private Node popNode() {
		return this.nodeStack.pop();
	}

	@SuppressWarnings("unchecked")
	private <T> T popNodeAs(final Class<T> desiredClass) {
		if (this.nodeStack.isEmpty()) {
			throw new RuntimeException(String.format("Cannot pop a %s from an empty stack.",
					desiredClass.getSimpleName()));
		}
		Node result = this.nodeStack.pop();
		if (!desiredClass.isAssignableFrom(result.getClass())) {
			throw new RuntimeException(String.format(
					"Wrong node type on stack: expected %s but popped %s",
					desiredClass.getSimpleName(), result.getClass().getSimpleName()));
		}
		return (T) result;
	}

	/**
	 * Adds the given node as a detail of the current stack node and pushes it
	 * onto the stack.
	 * 
	 * @param node
	 */
	private void pushAndAddNode(@Nonnull final Node node) {
		Node top = getNode();
		pushNode(node);
		addToNode(top, node);
	}

	private void pushNode(final Node node) {
		this.nodeStack.push(node);
	}

	private String unescapeLiteralString(final String rawText) {
		if (rawText == null || rawText.length() < 2) {
			return null;
		}
		String trimmed = rawText.substring(1, rawText.length() - 1);
		return trimmed.replaceAll("''", "'");
	}

}
