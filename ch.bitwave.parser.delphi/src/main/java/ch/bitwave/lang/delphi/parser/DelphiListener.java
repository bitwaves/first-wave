// Generated from ch\bitwave\lang\delphi\parser\Delphi.g4 by ANTLR 4.0
package ch.bitwave.lang.delphi.parser;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;

public interface DelphiListener extends ParseTreeListener {
	void enterMemberDestructorDeclaration(DelphiParser.MemberDestructorDeclarationContext ctx);
	void exitMemberDestructorDeclaration(DelphiParser.MemberDestructorDeclarationContext ctx);

	void enterConstantList(DelphiParser.ConstantListContext ctx);
	void exitConstantList(DelphiParser.ConstantListContext ctx);

	void enterUnsignedNumber(DelphiParser.UnsignedNumberContext ctx);
	void exitUnsignedNumber(DelphiParser.UnsignedNumberContext ctx);

	void enterArrayType(DelphiParser.ArrayTypeContext ctx);
	void exitArrayType(DelphiParser.ArrayTypeContext ctx);

	void enterQualifiedTypeIdentifier(DelphiParser.QualifiedTypeIdentifierContext ctx);
	void exitQualifiedTypeIdentifier(DelphiParser.QualifiedTypeIdentifierContext ctx);

	void enterExpressionList(DelphiParser.ExpressionListContext ctx);
	void exitExpressionList(DelphiParser.ExpressionListContext ctx);

	void enterLocalDirective(DelphiParser.LocalDirectiveContext ctx);
	void exitLocalDirective(DelphiParser.LocalDirectiveContext ctx);

	void enterDynamicDirective(DelphiParser.DynamicDirectiveContext ctx);
	void exitDynamicDirective(DelphiParser.DynamicDirectiveContext ctx);

	void enterCallConventionDirective(DelphiParser.CallConventionDirectiveContext ctx);
	void exitCallConventionDirective(DelphiParser.CallConventionDirectiveContext ctx);

	void enterDeprecatedDirective(DelphiParser.DeprecatedDirectiveContext ctx);
	void exitDeprecatedDirective(DelphiParser.DeprecatedDirectiveContext ctx);

	void enterVirtualDirective(DelphiParser.VirtualDirectiveContext ctx);
	void exitVirtualDirective(DelphiParser.VirtualDirectiveContext ctx);

	void enterTypeDeclaration(DelphiParser.TypeDeclarationContext ctx);
	void exitTypeDeclaration(DelphiParser.TypeDeclarationContext ctx);

	void enterStdcallDirective(DelphiParser.StdcallDirectiveContext ctx);
	void exitStdcallDirective(DelphiParser.StdcallDirectiveContext ctx);

	void enterCodeBlock(DelphiParser.CodeBlockContext ctx);
	void exitCodeBlock(DelphiParser.CodeBlockContext ctx);

	void enterSimpleStatement(DelphiParser.SimpleStatementContext ctx);
	void exitSimpleStatement(DelphiParser.SimpleStatementContext ctx);

	void enterClassMemberDeclaration(DelphiParser.ClassMemberDeclarationContext ctx);
	void exitClassMemberDeclaration(DelphiParser.ClassMemberDeclarationContext ctx);

	void enterBracketedExpression(DelphiParser.BracketedExpressionContext ctx);
	void exitBracketedExpression(DelphiParser.BracketedExpressionContext ctx);

	void enterVarargsDirective(DelphiParser.VarargsDirectiveContext ctx);
	void exitVarargsDirective(DelphiParser.VarargsDirectiveContext ctx);

	void enterTermOperator(DelphiParser.TermOperatorContext ctx);
	void exitTermOperator(DelphiParser.TermOperatorContext ctx);

	void enterVariableDeclarations(DelphiParser.VariableDeclarationsContext ctx);
	void exitVariableDeclarations(DelphiParser.VariableDeclarationsContext ctx);

	void enterParameterList(DelphiParser.ParameterListContext ctx);
	void exitParameterList(DelphiParser.ParameterListContext ctx);

	void enterMemberImplementation(DelphiParser.MemberImplementationContext ctx);
	void exitMemberImplementation(DelphiParser.MemberImplementationContext ctx);

	void enterMemberFunctionImplementation(DelphiParser.MemberFunctionImplementationContext ctx);
	void exitMemberFunctionImplementation(DelphiParser.MemberFunctionImplementationContext ctx);

	void enterWithStatement(DelphiParser.WithStatementContext ctx);
	void exitWithStatement(DelphiParser.WithStatementContext ctx);

	void enterAncestorDeclaration(DelphiParser.AncestorDeclarationContext ctx);
	void exitAncestorDeclaration(DelphiParser.AncestorDeclarationContext ctx);

	void enterElement(DelphiParser.ElementContext ctx);
	void exitElement(DelphiParser.ElementContext ctx);

	void enterRepeatStatement(DelphiParser.RepeatStatementContext ctx);
	void exitRepeatStatement(DelphiParser.RepeatStatementContext ctx);

	void enterScalarType(DelphiParser.ScalarTypeContext ctx);
	void exitScalarType(DelphiParser.ScalarTypeContext ctx);

	void enterDispIDDirective(DelphiParser.DispIDDirectiveContext ctx);
	void exitDispIDDirective(DelphiParser.DispIDDirectiveContext ctx);

	void enterProceduralDeclaration(DelphiParser.ProceduralDeclarationContext ctx);
	void exitProceduralDeclaration(DelphiParser.ProceduralDeclarationContext ctx);

	void enterLocalBlock(DelphiParser.LocalBlockContext ctx);
	void exitLocalBlock(DelphiParser.LocalBlockContext ctx);

	void enterUnsignedInteger(DelphiParser.UnsignedIntegerContext ctx);
	void exitUnsignedInteger(DelphiParser.UnsignedIntegerContext ctx);

	void enterVariantPart(DelphiParser.VariantPartContext ctx);
	void exitVariantPart(DelphiParser.VariantPartContext ctx);

	void enterForwardDirective(DelphiParser.ForwardDirectiveContext ctx);
	void exitForwardDirective(DelphiParser.ForwardDirectiveContext ctx);

	void enterUnsignedReal(DelphiParser.UnsignedRealContext ctx);
	void exitUnsignedReal(DelphiParser.UnsignedRealContext ctx);

	void enterVarSection(DelphiParser.VarSectionContext ctx);
	void exitVarSection(DelphiParser.VarSectionContext ctx);

	void enterRecordSection(DelphiParser.RecordSectionContext ctx);
	void exitRecordSection(DelphiParser.RecordSectionContext ctx);

	void enterFinallyClause(DelphiParser.FinallyClauseContext ctx);
	void exitFinallyClause(DelphiParser.FinallyClauseContext ctx);

	void enterRecordField(DelphiParser.RecordFieldContext ctx);
	void exitRecordField(DelphiParser.RecordFieldContext ctx);

	void enterInterfaceType(DelphiParser.InterfaceTypeContext ctx);
	void exitInterfaceType(DelphiParser.InterfaceTypeContext ctx);

	void enterForStatement(DelphiParser.ForStatementContext ctx);
	void exitForStatement(DelphiParser.ForStatementContext ctx);

	void enterLibraryDirective(DelphiParser.LibraryDirectiveContext ctx);
	void exitLibraryDirective(DelphiParser.LibraryDirectiveContext ctx);

	void enterVariant(DelphiParser.VariantContext ctx);
	void exitVariant(DelphiParser.VariantContext ctx);

	void enterInterfaceMemberDeclarations(DelphiParser.InterfaceMemberDeclarationsContext ctx);
	void exitInterfaceMemberDeclarations(DelphiParser.InterfaceMemberDeclarationsContext ctx);

	void enterDimensionQualifiers(DelphiParser.DimensionQualifiersContext ctx);
	void exitDimensionQualifiers(DelphiParser.DimensionQualifiersContext ctx);

	void enterFixedPart(DelphiParser.FixedPartContext ctx);
	void exitFixedPart(DelphiParser.FixedPartContext ctx);

	void enterExceptClause(DelphiParser.ExceptClauseContext ctx);
	void exitExceptClause(DelphiParser.ExceptClauseContext ctx);

	void enterFunctionType(DelphiParser.FunctionTypeContext ctx);
	void exitFunctionType(DelphiParser.FunctionTypeContext ctx);

	void enterExternalSpecifier(DelphiParser.ExternalSpecifierContext ctx);
	void exitExternalSpecifier(DelphiParser.ExternalSpecifierContext ctx);

	void enterMemberProcedureImplementation(DelphiParser.MemberProcedureImplementationContext ctx);
	void exitMemberProcedureImplementation(DelphiParser.MemberProcedureImplementationContext ctx);

	void enterFieldDeclaration(DelphiParser.FieldDeclarationContext ctx);
	void exitFieldDeclaration(DelphiParser.FieldDeclarationContext ctx);

	void enterExceptionHandler(DelphiParser.ExceptionHandlerContext ctx);
	void exitExceptionHandler(DelphiParser.ExceptionHandlerContext ctx);

	void enterPascalDirective(DelphiParser.PascalDirectiveContext ctx);
	void exitPascalDirective(DelphiParser.PascalDirectiveContext ctx);

	void enterReintroduceDirective(DelphiParser.ReintroduceDirectiveContext ctx);
	void exitReintroduceDirective(DelphiParser.ReintroduceDirectiveContext ctx);

	void enterFileType(DelphiParser.FileTypeContext ctx);
	void exitFileType(DelphiParser.FileTypeContext ctx);

	void enterCompoundStatement(DelphiParser.CompoundStatementContext ctx);
	void exitCompoundStatement(DelphiParser.CompoundStatementContext ctx);

	void enterCaseStatement(DelphiParser.CaseStatementContext ctx);
	void exitCaseStatement(DelphiParser.CaseStatementContext ctx);

	void enterInterfaceDeclarationSection(DelphiParser.InterfaceDeclarationSectionContext ctx);
	void exitInterfaceDeclarationSection(DelphiParser.InterfaceDeclarationSectionContext ctx);

	void enterImplementationBlock(DelphiParser.ImplementationBlockContext ctx);
	void exitImplementationBlock(DelphiParser.ImplementationBlockContext ctx);

	void enterFactor(DelphiParser.FactorContext ctx);
	void exitFactor(DelphiParser.FactorContext ctx);

	void enterStaticStringType(DelphiParser.StaticStringTypeContext ctx);
	void exitStaticStringType(DelphiParser.StaticStringTypeContext ctx);

	void enterStructuredStatement(DelphiParser.StructuredStatementContext ctx);
	void exitStructuredStatement(DelphiParser.StructuredStatementContext ctx);

	void enterMethodDirective(DelphiParser.MethodDirectiveContext ctx);
	void exitMethodDirective(DelphiParser.MethodDirectiveContext ctx);

	void enterLiteralString(DelphiParser.LiteralStringContext ctx);
	void exitLiteralString(DelphiParser.LiteralStringContext ctx);

	void enterClassOfClassType(DelphiParser.ClassOfClassTypeContext ctx);
	void exitClassOfClassType(DelphiParser.ClassOfClassTypeContext ctx);

	void enterInterfaceSection(DelphiParser.InterfaceSectionContext ctx);
	void exitInterfaceSection(DelphiParser.InterfaceSectionContext ctx);

	void enterGotoStatement(DelphiParser.GotoStatementContext ctx);
	void exitGotoStatement(DelphiParser.GotoStatementContext ctx);

	void enterRaiseException(DelphiParser.RaiseExceptionContext ctx);
	void exitRaiseException(DelphiParser.RaiseExceptionContext ctx);

	void enterSignedNumber(DelphiParser.SignedNumberContext ctx);
	void exitSignedNumber(DelphiParser.SignedNumberContext ctx);

	void enterRecordFieldList(DelphiParser.RecordFieldListContext ctx);
	void exitRecordFieldList(DelphiParser.RecordFieldListContext ctx);

	void enterFarDirective(DelphiParser.FarDirectiveContext ctx);
	void exitFarDirective(DelphiParser.FarDirectiveContext ctx);

	void enterTypeDefinition(DelphiParser.TypeDefinitionContext ctx);
	void exitTypeDefinition(DelphiParser.TypeDefinitionContext ctx);

	void enterFunctionDeclaration(DelphiParser.FunctionDeclarationContext ctx);
	void exitFunctionDeclaration(DelphiParser.FunctionDeclarationContext ctx);

	void enterPropertyAccessDeclaration(DelphiParser.PropertyAccessDeclarationContext ctx);
	void exitPropertyAccessDeclaration(DelphiParser.PropertyAccessDeclarationContext ctx);

	void enterVariable(DelphiParser.VariableContext ctx);
	void exitVariable(DelphiParser.VariableContext ctx);

	void enterGuidSpecifier(DelphiParser.GuidSpecifierContext ctx);
	void exitGuidSpecifier(DelphiParser.GuidSpecifierContext ctx);

	void enterInitializer(DelphiParser.InitializerContext ctx);
	void exitInitializer(DelphiParser.InitializerContext ctx);

	void enterExpression(DelphiParser.ExpressionContext ctx);
	void exitExpression(DelphiParser.ExpressionContext ctx);

	void enterAbstractDirective(DelphiParser.AbstractDirectiveContext ctx);
	void exitAbstractDirective(DelphiParser.AbstractDirectiveContext ctx);

	void enterExternalDirective(DelphiParser.ExternalDirectiveContext ctx);
	void exitExternalDirective(DelphiParser.ExternalDirectiveContext ctx);

	void enterExpressionOperator(DelphiParser.ExpressionOperatorContext ctx);
	void exitExpressionOperator(DelphiParser.ExpressionOperatorContext ctx);

	void enterMemberDestructorImplementation(DelphiParser.MemberDestructorImplementationContext ctx);
	void exitMemberDestructorImplementation(DelphiParser.MemberDestructorImplementationContext ctx);

	void enterFunctionImplementation(DelphiParser.FunctionImplementationContext ctx);
	void exitFunctionImplementation(DelphiParser.FunctionImplementationContext ctx);

	void enterPropertyDeclaration(DelphiParser.PropertyDeclarationContext ctx);
	void exitPropertyDeclaration(DelphiParser.PropertyDeclarationContext ctx);

	void enterParameterDeclaration(DelphiParser.ParameterDeclarationContext ctx);
	void exitParameterDeclaration(DelphiParser.ParameterDeclarationContext ctx);

	void enterQualifiedIdentifier(DelphiParser.QualifiedIdentifierContext ctx);
	void exitQualifiedIdentifier(DelphiParser.QualifiedIdentifierContext ctx);

	void enterType(DelphiParser.TypeContext ctx);
	void exitType(DelphiParser.TypeContext ctx);

	void enterParametersDeclaration(DelphiParser.ParametersDeclarationContext ctx);
	void exitParametersDeclaration(DelphiParser.ParametersDeclarationContext ctx);

	void enterRecordItem(DelphiParser.RecordItemContext ctx);
	void exitRecordItem(DelphiParser.RecordItemContext ctx);

	void enterMemberAccessStatement(DelphiParser.MemberAccessStatementContext ctx);
	void exitMemberAccessStatement(DelphiParser.MemberAccessStatementContext ctx);

	void enterTypeCheck(DelphiParser.TypeCheckContext ctx);
	void exitTypeCheck(DelphiParser.TypeCheckContext ctx);

	void enterPlatformDirective(DelphiParser.PlatformDirectiveContext ctx);
	void exitPlatformDirective(DelphiParser.PlatformDirectiveContext ctx);

	void enterSetConstant(DelphiParser.SetConstantContext ctx);
	void exitSetConstant(DelphiParser.SetConstantContext ctx);

	void enterVariableDeclaration(DelphiParser.VariableDeclarationContext ctx);
	void exitVariableDeclaration(DelphiParser.VariableDeclarationContext ctx);

	void enterCharLiteralConstant(DelphiParser.CharLiteralConstantContext ctx);
	void exitCharLiteralConstant(DelphiParser.CharLiteralConstantContext ctx);

	void enterFinalDirective(DelphiParser.FinalDirectiveContext ctx);
	void exitFinalDirective(DelphiParser.FinalDirectiveContext ctx);

	void enterFunctionCall(DelphiParser.FunctionCallContext ctx);
	void exitFunctionCall(DelphiParser.FunctionCallContext ctx);

	void enterMemberFunctionDeclaration(DelphiParser.MemberFunctionDeclarationContext ctx);
	void exitMemberFunctionDeclaration(DelphiParser.MemberFunctionDeclarationContext ctx);

	void enterMemberAccess(DelphiParser.MemberAccessContext ctx);
	void exitMemberAccess(DelphiParser.MemberAccessContext ctx);

	void enterCaseItem(DelphiParser.CaseItemContext ctx);
	void exitCaseItem(DelphiParser.CaseItemContext ctx);

	void enterDelphiUnit(DelphiParser.DelphiUnitContext ctx);
	void exitDelphiUnit(DelphiParser.DelphiUnitContext ctx);

	void enterProceduralImplementation(DelphiParser.ProceduralImplementationContext ctx);
	void exitProceduralImplementation(DelphiParser.ProceduralImplementationContext ctx);

	void enterRecordType(DelphiParser.RecordTypeContext ctx);
	void exitRecordType(DelphiParser.RecordTypeContext ctx);

	void enterDefaultValue(DelphiParser.DefaultValueContext ctx);
	void exitDefaultValue(DelphiParser.DefaultValueContext ctx);

	void enterLabelledStatement(DelphiParser.LabelledStatementContext ctx);
	void exitLabelledStatement(DelphiParser.LabelledStatementContext ctx);

	void enterConstDeclaration(DelphiParser.ConstDeclarationContext ctx);
	void exitConstDeclaration(DelphiParser.ConstDeclarationContext ctx);

	void enterSetType(DelphiParser.SetTypeContext ctx);
	void exitSetType(DelphiParser.SetTypeContext ctx);

	void enterDimensionQualifier(DelphiParser.DimensionQualifierContext ctx);
	void exitDimensionQualifier(DelphiParser.DimensionQualifierContext ctx);

	void enterMemberConstructorImplementation(DelphiParser.MemberConstructorImplementationContext ctx);
	void exitMemberConstructorImplementation(DelphiParser.MemberConstructorImplementationContext ctx);

	void enterAssemblerDirective(DelphiParser.AssemblerDirectiveContext ctx);
	void exitAssemblerDirective(DelphiParser.AssemblerDirectiveContext ctx);

	void enterWriteAccessorDeclaration(DelphiParser.WriteAccessorDeclarationContext ctx);
	void exitWriteAccessorDeclaration(DelphiParser.WriteAccessorDeclarationContext ctx);

	void enterLabel(DelphiParser.LabelContext ctx);
	void exitLabel(DelphiParser.LabelContext ctx);

	void enterOldCallConventionDirective(DelphiParser.OldCallConventionDirectiveContext ctx);
	void exitOldCallConventionDirective(DelphiParser.OldCallConventionDirectiveContext ctx);

	void enterTypeDeclarations(DelphiParser.TypeDeclarationsContext ctx);
	void exitTypeDeclarations(DelphiParser.TypeDeclarationsContext ctx);

	void enterExportDirective(DelphiParser.ExportDirectiveContext ctx);
	void exitExportDirective(DelphiParser.ExportDirectiveContext ctx);

	void enterComponentType(DelphiParser.ComponentTypeContext ctx);
	void exitComponentType(DelphiParser.ComponentTypeContext ctx);

	void enterCdeclDirective(DelphiParser.CdeclDirectiveContext ctx);
	void exitCdeclDirective(DelphiParser.CdeclDirectiveContext ctx);

	void enterScalarDeclarations(DelphiParser.ScalarDeclarationsContext ctx);
	void exitScalarDeclarations(DelphiParser.ScalarDeclarationsContext ctx);

	void enterEmptyStatement(DelphiParser.EmptyStatementContext ctx);
	void exitEmptyStatement(DelphiParser.EmptyStatementContext ctx);

	void enterSign(DelphiParser.SignContext ctx);
	void exitSign(DelphiParser.SignContext ctx);

	void enterStatement(DelphiParser.StatementContext ctx);
	void exitStatement(DelphiParser.StatementContext ctx);

	void enterInterfaceDeclarationBody(DelphiParser.InterfaceDeclarationBodyContext ctx);
	void exitInterfaceDeclarationBody(DelphiParser.InterfaceDeclarationBodyContext ctx);

	void enterTypeIdentifier(DelphiParser.TypeIdentifierContext ctx);
	void exitTypeIdentifier(DelphiParser.TypeIdentifierContext ctx);

	void enterPointerType(DelphiParser.PointerTypeContext ctx);
	void exitPointerType(DelphiParser.PointerTypeContext ctx);

	void enterConditionalStatement(DelphiParser.ConditionalStatementContext ctx);
	void exitConditionalStatement(DelphiParser.ConditionalStatementContext ctx);

	void enterHintingDirective(DelphiParser.HintingDirectiveContext ctx);
	void exitHintingDirective(DelphiParser.HintingDirectiveContext ctx);

	void enterImplementationDeclarationSection(DelphiParser.ImplementationDeclarationSectionContext ctx);
	void exitImplementationDeclarationSection(DelphiParser.ImplementationDeclarationSectionContext ctx);

	void enterRecordInitializer(DelphiParser.RecordInitializerContext ctx);
	void exitRecordInitializer(DelphiParser.RecordInitializerContext ctx);

	void enterPrimitiveFunctionConstant(DelphiParser.PrimitiveFunctionConstantContext ctx);
	void exitPrimitiveFunctionConstant(DelphiParser.PrimitiveFunctionConstantContext ctx);

	void enterConstSection(DelphiParser.ConstSectionContext ctx);
	void exitConstSection(DelphiParser.ConstSectionContext ctx);

	void enterStructuredType(DelphiParser.StructuredTypeContext ctx);
	void exitStructuredType(DelphiParser.StructuredTypeContext ctx);

	void enterSimpleExpression(DelphiParser.SimpleExpressionContext ctx);
	void exitSimpleExpression(DelphiParser.SimpleExpressionContext ctx);

	void enterClassDeclarationBody(DelphiParser.ClassDeclarationBodyContext ctx);
	void exitClassDeclarationBody(DelphiParser.ClassDeclarationBodyContext ctx);

	void enterIdentifier(DelphiParser.IdentifierContext ctx);
	void exitIdentifier(DelphiParser.IdentifierContext ctx);

	void enterTryBlock(DelphiParser.TryBlockContext ctx);
	void exitTryBlock(DelphiParser.TryBlockContext ctx);

	void enterRegisterDirective(DelphiParser.RegisterDirectiveContext ctx);
	void exitRegisterDirective(DelphiParser.RegisterDirectiveContext ctx);

	void enterTypeSection(DelphiParser.TypeSectionContext ctx);
	void exitTypeSection(DelphiParser.TypeSectionContext ctx);

	void enterExperimentalDirective(DelphiParser.ExperimentalDirectiveContext ctx);
	void exitExperimentalDirective(DelphiParser.ExperimentalDirectiveContext ctx);

	void enterFunctionDirective(DelphiParser.FunctionDirectiveContext ctx);
	void exitFunctionDirective(DelphiParser.FunctionDirectiveContext ctx);

	void enterUsesSection(DelphiParser.UsesSectionContext ctx);
	void exitUsesSection(DelphiParser.UsesSectionContext ctx);

	void enterSafecallDirective(DelphiParser.SafecallDirectiveContext ctx);
	void exitSafecallDirective(DelphiParser.SafecallDirectiveContext ctx);

	void enterMessageDirective(DelphiParser.MessageDirectiveContext ctx);
	void exitMessageDirective(DelphiParser.MessageDirectiveContext ctx);

	void enterParamModifier(DelphiParser.ParamModifierContext ctx);
	void exitParamModifier(DelphiParser.ParamModifierContext ctx);

	void enterSignedFactor(DelphiParser.SignedFactorContext ctx);
	void exitSignedFactor(DelphiParser.SignedFactorContext ctx);

	void enterUnlabelledStatement(DelphiParser.UnlabelledStatementContext ctx);
	void exitUnlabelledStatement(DelphiParser.UnlabelledStatementContext ctx);

	void enterIdentifierList(DelphiParser.IdentifierListContext ctx);
	void exitIdentifierList(DelphiParser.IdentifierListContext ctx);

	void enterTypeSpecifier(DelphiParser.TypeSpecifierContext ctx);
	void exitTypeSpecifier(DelphiParser.TypeSpecifierContext ctx);

	void enterExceptionHandlers(DelphiParser.ExceptionHandlersContext ctx);
	void exitExceptionHandlers(DelphiParser.ExceptionHandlersContext ctx);

	void enterNegation(DelphiParser.NegationContext ctx);
	void exitNegation(DelphiParser.NegationContext ctx);

	void enterElementList(DelphiParser.ElementListContext ctx);
	void exitElementList(DelphiParser.ElementListContext ctx);

	void enterRepetetiveStatement(DelphiParser.RepetetiveStatementContext ctx);
	void exitRepetetiveStatement(DelphiParser.RepetetiveStatementContext ctx);

	void enterSubrangeType(DelphiParser.SubrangeTypeContext ctx);
	void exitSubrangeType(DelphiParser.SubrangeTypeContext ctx);

	void enterProcedureDeclaration(DelphiParser.ProcedureDeclarationContext ctx);
	void exitProcedureDeclaration(DelphiParser.ProcedureDeclarationContext ctx);

	void enterProcedureImplementation(DelphiParser.ProcedureImplementationContext ctx);
	void exitProcedureImplementation(DelphiParser.ProcedureImplementationContext ctx);

	void enterMemberInvocation(DelphiParser.MemberInvocationContext ctx);
	void exitMemberInvocation(DelphiParser.MemberInvocationContext ctx);

	void enterScalarDeclaration(DelphiParser.ScalarDeclarationContext ctx);
	void exitScalarDeclaration(DelphiParser.ScalarDeclarationContext ctx);

	void enterStatements(DelphiParser.StatementsContext ctx);
	void exitStatements(DelphiParser.StatementsContext ctx);

	void enterNearDirective(DelphiParser.NearDirectiveContext ctx);
	void exitNearDirective(DelphiParser.NearDirectiveContext ctx);

	void enterRecordItemList(DelphiParser.RecordItemListContext ctx);
	void exitRecordItemList(DelphiParser.RecordItemListContext ctx);

	void enterOverloadDirective(DelphiParser.OverloadDirectiveContext ctx);
	void exitOverloadDirective(DelphiParser.OverloadDirectiveContext ctx);

	void enterBindingDirective(DelphiParser.BindingDirectiveContext ctx);
	void exitBindingDirective(DelphiParser.BindingDirectiveContext ctx);

	void enterStaticDirective(DelphiParser.StaticDirectiveContext ctx);
	void exitStaticDirective(DelphiParser.StaticDirectiveContext ctx);

	void enterUnpackedStructuredType(DelphiParser.UnpackedStructuredTypeContext ctx);
	void exitUnpackedStructuredType(DelphiParser.UnpackedStructuredTypeContext ctx);

	void enterMethodDeclaration(DelphiParser.MethodDeclarationContext ctx);
	void exitMethodDeclaration(DelphiParser.MethodDeclarationContext ctx);

	void enterReadAccessorDeclaration(DelphiParser.ReadAccessorDeclarationContext ctx);
	void exitReadAccessorDeclaration(DelphiParser.ReadAccessorDeclarationContext ctx);

	void enterImplementationBlocks(DelphiParser.ImplementationBlocksContext ctx);
	void exitImplementationBlocks(DelphiParser.ImplementationBlocksContext ctx);

	void enterCaseItems(DelphiParser.CaseItemsContext ctx);
	void exitCaseItems(DelphiParser.CaseItemsContext ctx);

	void enterConstant(DelphiParser.ConstantContext ctx);
	void exitConstant(DelphiParser.ConstantContext ctx);

	void enterLabelSection(DelphiParser.LabelSectionContext ctx);
	void exitLabelSection(DelphiParser.LabelSectionContext ctx);

	void enterSimpleOperator(DelphiParser.SimpleOperatorContext ctx);
	void exitSimpleOperator(DelphiParser.SimpleOperatorContext ctx);

	void enterOverrideDirective(DelphiParser.OverrideDirectiveContext ctx);
	void exitOverrideDirective(DelphiParser.OverrideDirectiveContext ctx);

	void enterClassType(DelphiParser.ClassTypeContext ctx);
	void exitClassType(DelphiParser.ClassTypeContext ctx);

	void enterRecordFieldDeclaration(DelphiParser.RecordFieldDeclarationContext ctx);
	void exitRecordFieldDeclaration(DelphiParser.RecordFieldDeclarationContext ctx);

	void enterProcedureType(DelphiParser.ProcedureTypeContext ctx);
	void exitProcedureType(DelphiParser.ProcedureTypeContext ctx);

	void enterMemberProcedureDeclaration(DelphiParser.MemberProcedureDeclarationContext ctx);
	void exitMemberProcedureDeclaration(DelphiParser.MemberProcedureDeclarationContext ctx);

	void enterArgumentList(DelphiParser.ArgumentListContext ctx);
	void exitArgumentList(DelphiParser.ArgumentListContext ctx);

	void enterAssignmentStatement(DelphiParser.AssignmentStatementContext ctx);
	void exitAssignmentStatement(DelphiParser.AssignmentStatementContext ctx);

	void enterIfStatement(DelphiParser.IfStatementContext ctx);
	void exitIfStatement(DelphiParser.IfStatementContext ctx);

	void enterMemberConstructorDeclaration(DelphiParser.MemberConstructorDeclarationContext ctx);
	void exitMemberConstructorDeclaration(DelphiParser.MemberConstructorDeclarationContext ctx);

	void enterArrayInitializer(DelphiParser.ArrayInitializerContext ctx);
	void exitArrayInitializer(DelphiParser.ArrayInitializerContext ctx);

	void enterTerm(DelphiParser.TermContext ctx);
	void exitTerm(DelphiParser.TermContext ctx);

	void enterWhileStatement(DelphiParser.WhileStatementContext ctx);
	void exitWhileStatement(DelphiParser.WhileStatementContext ctx);

	void enterSimpleType(DelphiParser.SimpleTypeContext ctx);
	void exitSimpleType(DelphiParser.SimpleTypeContext ctx);

	void enterElseCase(DelphiParser.ElseCaseContext ctx);
	void exitElseCase(DelphiParser.ElseCaseContext ctx);

	void enterImplementationSection(DelphiParser.ImplementationSectionContext ctx);
	void exitImplementationSection(DelphiParser.ImplementationSectionContext ctx);

	void enterInlineDirective(DelphiParser.InlineDirectiveContext ctx);
	void exitInlineDirective(DelphiParser.InlineDirectiveContext ctx);

	void enterDefaultModifier(DelphiParser.DefaultModifierContext ctx);
	void exitDefaultModifier(DelphiParser.DefaultModifierContext ctx);

	void enterNil(DelphiParser.NilContext ctx);
	void exitNil(DelphiParser.NilContext ctx);

	void enterInterfaceMemberDeclaration(DelphiParser.InterfaceMemberDeclarationContext ctx);
	void exitInterfaceMemberDeclaration(DelphiParser.InterfaceMemberDeclarationContext ctx);
}