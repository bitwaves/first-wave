package ch.bitwave.lang.delphi.ast;

public interface DelphiSyntaxVisitor {
	void visitAncestorDeclaration(final AncestorDeclaration node);

	void visitArrayInitializer(ArrayInitializer node);

	void visitArrayType(final ArrayType node);

	void visitAssignmentStatement(final AssignmentStatement node);

	void visitBracketedExpression(final BracketedExpression node);

	void visitCaseItem(CaseItem node);

	void visitCaseStatement(final CaseStatement node);

	void visitClassifierDeclaration(final ClassifierDeclaration node);

	void visitClassType(final ClassType node);

	void visitCodeBlock(final CodeBlock node);

	void visitCompoundStatement(final CompoundStatement node);

	void visitConstDeclaration(final ConstDeclaration node);

	void visitConstSection(ConstSection node);

	void visitDimensionQualifier(final DimensionQualifier node);

	void visitElseCase(ElseCase node);

	void visitEmptyStatement(EmptyStatement node);

	void visitExpression(final Expression node);

	void visitFactor(final Factor node);

	void visitFieldDeclaration(final FieldDeclaration node);

	void visitForStatement(ForStatement node);

	void visitFunctionCall(final FunctionCall node);

	void visitFunctionImplementation(FunctionImplementation node);

	void visitGotoStatement(GotoStatement node);

	void visitGuidSpecifier(GuidSpecifier node);

	void visitIdentifier(Identifier node);

	void visitIfStatement(IfStatement node);

	void visitImplementationSection(final ImplementationSection node);

	void visitInterfaceSection(final InterfaceSection node);

	void visitInterfaceType(final InterfaceType node);

	void visitLabel(Label node);

	void visitLabelledStatement(final LabelledStatement node);

	void visitLabelSection(LabelSection labelSection);

	void visitLiteralString(final LiteralString node);

	void visitMemberAccess(MemberAccess node);

	void visitMemberConstructorDeclaration(MemberConstructorDeclaration node);

	void visitMemberConstructorImplementation(final MemberConstructorImplementation node);

	void visitMemberDestructorDeclaration(MemberDestructorDeclaration node);

	void visitMemberDestructorImplementation(final MemberDestructorImplementation node);

	void visitMemberFunctionDeclaration(MemberFunctionDeclaration node);

	void visitMemberFunctionImplementation(final MemberFunctionImplementation node);

	void visitMemberInvocation(final MemberInvocation node);

	void visitMemberProcedureDeclaration(MemberProcedureDeclaration node);

	void visitMemberProcedureImplementation(final MemberProcedureImplementation node);

	void visitMethodDirective(MethodDirective node);

	void visitNil(Nil node);

	void visitParameterDeclaration(final ParameterDeclaration node);

	void visitPointerType(PointerType node);

	void visitProcedureImplementation(ProcedureImplementation node);

	void visitProcedureType(ProcedureType node);

	void visitPropertyDeclaration(final PropertyDeclaration node);

	void visitQualifiedIdentifier(final QualifiedIdentifier node);

	void visitRaiseExceptionStatement(RaiseExceptionStatement node);

	void visitRecordInitializer(RecordInitializer node);

	void visitRecordType(RecordType node);

	void visitRepeatStatement(RepeatStatement node);

	void visitScalarDeclaration(ScalarDeclaration node);

	void visitSetConstant(SetConstant node);

	void visitSetType(SetType node);

	void visitSignedFactor(final SignedFactor node);

	void visitSignedNumber(final SignedNumber node);

	void visitSimpleExpression(final SimpleExpression node);

	void visitSimpleExpressionOperator(SimpleExpressionOperator node);

	void visitStaticStringType(StaticStringType node);

	void visitSubrangeType(SubrangeType node);

	void visitTerm(final Term node);

	void visitTermOperator(TermOperator node);

	void visitTryBlock(TryBlock node);

	void visitTypeCheck(TypeCheck node);

	void visitTypeDeclaration(final TypeDeclaration node);

	void visitTypeIdentifier(TypeIdentifier node);

	void visitTypeSection(final TypeSection node);

	void visitUnit(final Unit node);

	void visitUnsignedInteger(UnsignedInteger node);

	void visitUnsignedReal(final UnsignedReal node);

	void visitUsesSection(final UsesSection node);

	void visitVariable(final Variable node);

	void visitVariableDeclaration(VariableDeclaration node);

	void visitVarSection(VarSection node);

	void visitWhileStatement(WhileStatement node);

	void visitWithStatement(WithStatement node);

	void visitInitializer(Initializer node);

	void visitElement(Element node);

	void visitFinallyClause(FinallyClause node);

	void visitRecordField(RecordField node);

	void visitRecordSection(RecordSection node);

	void visitScalarType(ScalarType node);

	void visitVariantPart(VariantPart node);

	void visitPropertyAccessDeclaration(PropertyAccessDeclaration node);

	void visitWriteAccessorDeclaration(WriteAccessorDeclaration node);

	void visitReadAccessorDeclaration(ReadAccessorDeclaration node);

	void visitVariant(Variant node);

	void visitFixedPart(FixedPart node);

	void visitRecordFieldDeclaration(RecordFieldDeclaration node);

	void visitMemberAccessStatement(MemberAccessStatement node);

	void visitBlockComment(BlockComment node);

	void visitLineComment(LineComment node);

	void visitPropertyModifier(PropertyModifier node);

	void visitFunctionDirective(FunctionDirective node);

	void visitFunctionType(FunctionType node);

	void visitExceptClause(ExceptClause node);

	void visitExceptionHandler(ExceptionHandler node);

	void visitRecordItem(RecordItem node);

	void visitFunctionDeclaration(FunctionDeclaration node);

	void visitProcedureDeclaration(ProcedureDeclaration node);

	void visitClassOfClassType(ClassOfClassType node);

	void visitQualifiedTypeIdentifier(QualifiedTypeIdentifier node);

	void visitPrimitiveFunctionConstant(PrimitiveFunctionConstant node);

}
