// Generated from ch\bitwave\lang\delphi\parser\Delphi.g4 by ANTLR 4.0
package ch.bitwave.lang.delphi.parser;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface DelphiVisitor<T> extends ParseTreeVisitor<T> {
	T visitMemberDestructorDeclaration(DelphiParser.MemberDestructorDeclarationContext ctx);

	T visitConstantList(DelphiParser.ConstantListContext ctx);

	T visitUnsignedNumber(DelphiParser.UnsignedNumberContext ctx);

	T visitArrayType(DelphiParser.ArrayTypeContext ctx);

	T visitQualifiedTypeIdentifier(DelphiParser.QualifiedTypeIdentifierContext ctx);

	T visitExpressionList(DelphiParser.ExpressionListContext ctx);

	T visitLocalDirective(DelphiParser.LocalDirectiveContext ctx);

	T visitDynamicDirective(DelphiParser.DynamicDirectiveContext ctx);

	T visitCallConventionDirective(DelphiParser.CallConventionDirectiveContext ctx);

	T visitDeprecatedDirective(DelphiParser.DeprecatedDirectiveContext ctx);

	T visitVirtualDirective(DelphiParser.VirtualDirectiveContext ctx);

	T visitTypeDeclaration(DelphiParser.TypeDeclarationContext ctx);

	T visitStdcallDirective(DelphiParser.StdcallDirectiveContext ctx);

	T visitCodeBlock(DelphiParser.CodeBlockContext ctx);

	T visitSimpleStatement(DelphiParser.SimpleStatementContext ctx);

	T visitClassMemberDeclaration(DelphiParser.ClassMemberDeclarationContext ctx);

	T visitBracketedExpression(DelphiParser.BracketedExpressionContext ctx);

	T visitVarargsDirective(DelphiParser.VarargsDirectiveContext ctx);

	T visitTermOperator(DelphiParser.TermOperatorContext ctx);

	T visitVariableDeclarations(DelphiParser.VariableDeclarationsContext ctx);

	T visitParameterList(DelphiParser.ParameterListContext ctx);

	T visitMemberImplementation(DelphiParser.MemberImplementationContext ctx);

	T visitMemberFunctionImplementation(DelphiParser.MemberFunctionImplementationContext ctx);

	T visitWithStatement(DelphiParser.WithStatementContext ctx);

	T visitAncestorDeclaration(DelphiParser.AncestorDeclarationContext ctx);

	T visitElement(DelphiParser.ElementContext ctx);

	T visitRepeatStatement(DelphiParser.RepeatStatementContext ctx);

	T visitScalarType(DelphiParser.ScalarTypeContext ctx);

	T visitDispIDDirective(DelphiParser.DispIDDirectiveContext ctx);

	T visitProceduralDeclaration(DelphiParser.ProceduralDeclarationContext ctx);

	T visitLocalBlock(DelphiParser.LocalBlockContext ctx);

	T visitUnsignedInteger(DelphiParser.UnsignedIntegerContext ctx);

	T visitVariantPart(DelphiParser.VariantPartContext ctx);

	T visitForwardDirective(DelphiParser.ForwardDirectiveContext ctx);

	T visitUnsignedReal(DelphiParser.UnsignedRealContext ctx);

	T visitVarSection(DelphiParser.VarSectionContext ctx);

	T visitRecordSection(DelphiParser.RecordSectionContext ctx);

	T visitFinallyClause(DelphiParser.FinallyClauseContext ctx);

	T visitRecordField(DelphiParser.RecordFieldContext ctx);

	T visitInterfaceType(DelphiParser.InterfaceTypeContext ctx);

	T visitForStatement(DelphiParser.ForStatementContext ctx);

	T visitLibraryDirective(DelphiParser.LibraryDirectiveContext ctx);

	T visitVariant(DelphiParser.VariantContext ctx);

	T visitInterfaceMemberDeclarations(DelphiParser.InterfaceMemberDeclarationsContext ctx);

	T visitDimensionQualifiers(DelphiParser.DimensionQualifiersContext ctx);

	T visitFixedPart(DelphiParser.FixedPartContext ctx);

	T visitExceptClause(DelphiParser.ExceptClauseContext ctx);

	T visitFunctionType(DelphiParser.FunctionTypeContext ctx);

	T visitExternalSpecifier(DelphiParser.ExternalSpecifierContext ctx);

	T visitMemberProcedureImplementation(DelphiParser.MemberProcedureImplementationContext ctx);

	T visitFieldDeclaration(DelphiParser.FieldDeclarationContext ctx);

	T visitExceptionHandler(DelphiParser.ExceptionHandlerContext ctx);

	T visitPascalDirective(DelphiParser.PascalDirectiveContext ctx);

	T visitReintroduceDirective(DelphiParser.ReintroduceDirectiveContext ctx);

	T visitFileType(DelphiParser.FileTypeContext ctx);

	T visitCompoundStatement(DelphiParser.CompoundStatementContext ctx);

	T visitCaseStatement(DelphiParser.CaseStatementContext ctx);

	T visitInterfaceDeclarationSection(DelphiParser.InterfaceDeclarationSectionContext ctx);

	T visitImplementationBlock(DelphiParser.ImplementationBlockContext ctx);

	T visitFactor(DelphiParser.FactorContext ctx);

	T visitStaticStringType(DelphiParser.StaticStringTypeContext ctx);

	T visitStructuredStatement(DelphiParser.StructuredStatementContext ctx);

	T visitMethodDirective(DelphiParser.MethodDirectiveContext ctx);

	T visitLiteralString(DelphiParser.LiteralStringContext ctx);

	T visitClassOfClassType(DelphiParser.ClassOfClassTypeContext ctx);

	T visitInterfaceSection(DelphiParser.InterfaceSectionContext ctx);

	T visitGotoStatement(DelphiParser.GotoStatementContext ctx);

	T visitRaiseException(DelphiParser.RaiseExceptionContext ctx);

	T visitSignedNumber(DelphiParser.SignedNumberContext ctx);

	T visitRecordFieldList(DelphiParser.RecordFieldListContext ctx);

	T visitFarDirective(DelphiParser.FarDirectiveContext ctx);

	T visitTypeDefinition(DelphiParser.TypeDefinitionContext ctx);

	T visitFunctionDeclaration(DelphiParser.FunctionDeclarationContext ctx);

	T visitPropertyAccessDeclaration(DelphiParser.PropertyAccessDeclarationContext ctx);

	T visitVariable(DelphiParser.VariableContext ctx);

	T visitGuidSpecifier(DelphiParser.GuidSpecifierContext ctx);

	T visitInitializer(DelphiParser.InitializerContext ctx);

	T visitExpression(DelphiParser.ExpressionContext ctx);

	T visitAbstractDirective(DelphiParser.AbstractDirectiveContext ctx);

	T visitExternalDirective(DelphiParser.ExternalDirectiveContext ctx);

	T visitExpressionOperator(DelphiParser.ExpressionOperatorContext ctx);

	T visitMemberDestructorImplementation(DelphiParser.MemberDestructorImplementationContext ctx);

	T visitFunctionImplementation(DelphiParser.FunctionImplementationContext ctx);

	T visitPropertyDeclaration(DelphiParser.PropertyDeclarationContext ctx);

	T visitParameterDeclaration(DelphiParser.ParameterDeclarationContext ctx);

	T visitQualifiedIdentifier(DelphiParser.QualifiedIdentifierContext ctx);

	T visitType(DelphiParser.TypeContext ctx);

	T visitParametersDeclaration(DelphiParser.ParametersDeclarationContext ctx);

	T visitRecordItem(DelphiParser.RecordItemContext ctx);

	T visitMemberAccessStatement(DelphiParser.MemberAccessStatementContext ctx);

	T visitTypeCheck(DelphiParser.TypeCheckContext ctx);

	T visitPlatformDirective(DelphiParser.PlatformDirectiveContext ctx);

	T visitSetConstant(DelphiParser.SetConstantContext ctx);

	T visitVariableDeclaration(DelphiParser.VariableDeclarationContext ctx);

	T visitCharLiteralConstant(DelphiParser.CharLiteralConstantContext ctx);

	T visitFinalDirective(DelphiParser.FinalDirectiveContext ctx);

	T visitFunctionCall(DelphiParser.FunctionCallContext ctx);

	T visitMemberFunctionDeclaration(DelphiParser.MemberFunctionDeclarationContext ctx);

	T visitMemberAccess(DelphiParser.MemberAccessContext ctx);

	T visitCaseItem(DelphiParser.CaseItemContext ctx);

	T visitDelphiUnit(DelphiParser.DelphiUnitContext ctx);

	T visitProceduralImplementation(DelphiParser.ProceduralImplementationContext ctx);

	T visitRecordType(DelphiParser.RecordTypeContext ctx);

	T visitDefaultValue(DelphiParser.DefaultValueContext ctx);

	T visitLabelledStatement(DelphiParser.LabelledStatementContext ctx);

	T visitConstDeclaration(DelphiParser.ConstDeclarationContext ctx);

	T visitSetType(DelphiParser.SetTypeContext ctx);

	T visitDimensionQualifier(DelphiParser.DimensionQualifierContext ctx);

	T visitMemberConstructorImplementation(DelphiParser.MemberConstructorImplementationContext ctx);

	T visitAssemblerDirective(DelphiParser.AssemblerDirectiveContext ctx);

	T visitWriteAccessorDeclaration(DelphiParser.WriteAccessorDeclarationContext ctx);

	T visitLabel(DelphiParser.LabelContext ctx);

	T visitOldCallConventionDirective(DelphiParser.OldCallConventionDirectiveContext ctx);

	T visitTypeDeclarations(DelphiParser.TypeDeclarationsContext ctx);

	T visitExportDirective(DelphiParser.ExportDirectiveContext ctx);

	T visitComponentType(DelphiParser.ComponentTypeContext ctx);

	T visitCdeclDirective(DelphiParser.CdeclDirectiveContext ctx);

	T visitScalarDeclarations(DelphiParser.ScalarDeclarationsContext ctx);

	T visitEmptyStatement(DelphiParser.EmptyStatementContext ctx);

	T visitSign(DelphiParser.SignContext ctx);

	T visitStatement(DelphiParser.StatementContext ctx);

	T visitInterfaceDeclarationBody(DelphiParser.InterfaceDeclarationBodyContext ctx);

	T visitTypeIdentifier(DelphiParser.TypeIdentifierContext ctx);

	T visitPointerType(DelphiParser.PointerTypeContext ctx);

	T visitConditionalStatement(DelphiParser.ConditionalStatementContext ctx);

	T visitHintingDirective(DelphiParser.HintingDirectiveContext ctx);

	T visitImplementationDeclarationSection(DelphiParser.ImplementationDeclarationSectionContext ctx);

	T visitRecordInitializer(DelphiParser.RecordInitializerContext ctx);

	T visitPrimitiveFunctionConstant(DelphiParser.PrimitiveFunctionConstantContext ctx);

	T visitConstSection(DelphiParser.ConstSectionContext ctx);

	T visitStructuredType(DelphiParser.StructuredTypeContext ctx);

	T visitSimpleExpression(DelphiParser.SimpleExpressionContext ctx);

	T visitClassDeclarationBody(DelphiParser.ClassDeclarationBodyContext ctx);

	T visitIdentifier(DelphiParser.IdentifierContext ctx);

	T visitTryBlock(DelphiParser.TryBlockContext ctx);

	T visitRegisterDirective(DelphiParser.RegisterDirectiveContext ctx);

	T visitTypeSection(DelphiParser.TypeSectionContext ctx);

	T visitExperimentalDirective(DelphiParser.ExperimentalDirectiveContext ctx);

	T visitFunctionDirective(DelphiParser.FunctionDirectiveContext ctx);

	T visitUsesSection(DelphiParser.UsesSectionContext ctx);

	T visitSafecallDirective(DelphiParser.SafecallDirectiveContext ctx);

	T visitMessageDirective(DelphiParser.MessageDirectiveContext ctx);

	T visitParamModifier(DelphiParser.ParamModifierContext ctx);

	T visitSignedFactor(DelphiParser.SignedFactorContext ctx);

	T visitUnlabelledStatement(DelphiParser.UnlabelledStatementContext ctx);

	T visitIdentifierList(DelphiParser.IdentifierListContext ctx);

	T visitTypeSpecifier(DelphiParser.TypeSpecifierContext ctx);

	T visitExceptionHandlers(DelphiParser.ExceptionHandlersContext ctx);

	T visitNegation(DelphiParser.NegationContext ctx);

	T visitElementList(DelphiParser.ElementListContext ctx);

	T visitRepetetiveStatement(DelphiParser.RepetetiveStatementContext ctx);

	T visitSubrangeType(DelphiParser.SubrangeTypeContext ctx);

	T visitProcedureDeclaration(DelphiParser.ProcedureDeclarationContext ctx);

	T visitProcedureImplementation(DelphiParser.ProcedureImplementationContext ctx);

	T visitMemberInvocation(DelphiParser.MemberInvocationContext ctx);

	T visitScalarDeclaration(DelphiParser.ScalarDeclarationContext ctx);

	T visitStatements(DelphiParser.StatementsContext ctx);

	T visitNearDirective(DelphiParser.NearDirectiveContext ctx);

	T visitRecordItemList(DelphiParser.RecordItemListContext ctx);

	T visitOverloadDirective(DelphiParser.OverloadDirectiveContext ctx);

	T visitBindingDirective(DelphiParser.BindingDirectiveContext ctx);

	T visitStaticDirective(DelphiParser.StaticDirectiveContext ctx);

	T visitUnpackedStructuredType(DelphiParser.UnpackedStructuredTypeContext ctx);

	T visitMethodDeclaration(DelphiParser.MethodDeclarationContext ctx);

	T visitReadAccessorDeclaration(DelphiParser.ReadAccessorDeclarationContext ctx);

	T visitImplementationBlocks(DelphiParser.ImplementationBlocksContext ctx);

	T visitCaseItems(DelphiParser.CaseItemsContext ctx);

	T visitConstant(DelphiParser.ConstantContext ctx);

	T visitLabelSection(DelphiParser.LabelSectionContext ctx);

	T visitSimpleOperator(DelphiParser.SimpleOperatorContext ctx);

	T visitOverrideDirective(DelphiParser.OverrideDirectiveContext ctx);

	T visitClassType(DelphiParser.ClassTypeContext ctx);

	T visitRecordFieldDeclaration(DelphiParser.RecordFieldDeclarationContext ctx);

	T visitProcedureType(DelphiParser.ProcedureTypeContext ctx);

	T visitMemberProcedureDeclaration(DelphiParser.MemberProcedureDeclarationContext ctx);

	T visitArgumentList(DelphiParser.ArgumentListContext ctx);

	T visitAssignmentStatement(DelphiParser.AssignmentStatementContext ctx);

	T visitIfStatement(DelphiParser.IfStatementContext ctx);

	T visitMemberConstructorDeclaration(DelphiParser.MemberConstructorDeclarationContext ctx);

	T visitArrayInitializer(DelphiParser.ArrayInitializerContext ctx);

	T visitTerm(DelphiParser.TermContext ctx);

	T visitWhileStatement(DelphiParser.WhileStatementContext ctx);

	T visitSimpleType(DelphiParser.SimpleTypeContext ctx);

	T visitElseCase(DelphiParser.ElseCaseContext ctx);

	T visitImplementationSection(DelphiParser.ImplementationSectionContext ctx);

	T visitInlineDirective(DelphiParser.InlineDirectiveContext ctx);

	T visitDefaultModifier(DelphiParser.DefaultModifierContext ctx);

	T visitNil(DelphiParser.NilContext ctx);

	T visitInterfaceMemberDeclaration(DelphiParser.InterfaceMemberDeclarationContext ctx);
}