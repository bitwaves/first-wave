package ch.bitwave.mim.integration.delphi;

import java.util.Stack;

import javax.annotation.Nonnull;

import org.apache.commons.lang.StringUtils;

import ch.bitwave.lang.delphi.ast.AncestorDeclaration;
import ch.bitwave.lang.delphi.ast.ArrayType;
import ch.bitwave.lang.delphi.ast.ClassType;
import ch.bitwave.lang.delphi.ast.ClassifierDeclaration;
import ch.bitwave.lang.delphi.ast.DelphiSyntaxAdapter;
import ch.bitwave.lang.delphi.ast.FieldDeclaration;
import ch.bitwave.lang.delphi.ast.Identifier;
import ch.bitwave.lang.delphi.ast.InterfaceSection;
import ch.bitwave.lang.delphi.ast.InterfaceType;
import ch.bitwave.lang.delphi.ast.MemberConstructorDeclaration;
import ch.bitwave.lang.delphi.ast.MemberConstructorImplementation;
import ch.bitwave.lang.delphi.ast.MemberDestructorDeclaration;
import ch.bitwave.lang.delphi.ast.MemberDestructorImplementation;
import ch.bitwave.lang.delphi.ast.MemberFunctionDeclaration;
import ch.bitwave.lang.delphi.ast.MemberFunctionImplementation;
import ch.bitwave.lang.delphi.ast.MemberProcedureDeclaration;
import ch.bitwave.lang.delphi.ast.MemberProcedureImplementation;
import ch.bitwave.lang.delphi.ast.MethodDeclaration;
import ch.bitwave.lang.delphi.ast.Node;
import ch.bitwave.lang.delphi.ast.QualifiedIdentifier;
import ch.bitwave.lang.delphi.ast.Type;
import ch.bitwave.lang.delphi.ast.TypeDeclaration;
import ch.bitwave.lang.delphi.ast.Unit;
import ch.bitwave.lang.delphi.ast.UsesSection;
import ch.bitwave.lang.delphi.ast.VariableDeclaration;
import ch.bitwave.lang.delphi.mapping.DelphiSyntaxWriter;
import ch.bitwave.mim.m2.core.Classifier;
import ch.bitwave.mim.m2.core.Dependent;
import ch.bitwave.mim.m2.core.Generalization;
import ch.bitwave.mim.m2.core.MimClass;
import ch.bitwave.mim.m2.core.MimElement;
import ch.bitwave.mim.m2.core.MimPackage;
import ch.bitwave.mim.m2.core.MimType;
import ch.bitwave.mim.m2.core.NamedElement;
import ch.bitwave.mim.m2.core.Operation;
import ch.bitwave.mim.m2.core.Property;
import ch.bitwave.mim.m2.core.StubClass;
import ch.bitwave.mim.m2.core.StubType;
import ch.bitwave.mim.m2.core.UnknownMemberException;
import ch.bitwave.mim.m2.profiles.DelphiProfile;

/**
 * Maps a Delphi AST into a Mim M2 model.
 */
public class DelphiASTMapper extends DelphiSyntaxAdapter {

	// private static final Logger LOGGER =
	// Logger.getLogger(DelphiMappingVisitor.class
	// .getSimpleName());
	private Stack<MimElement> stack;
	DelphiProfile profile;

	public DelphiASTMapper(final DelphiProfile profile, final MimPackage parent) {
		this.stack = new Stack<MimElement>();
		this.profile = profile;
		this.stack.push(parent);
	}

	private void pop(final MimElement element) {
		this.stack.pop();
	}

	private void push(final MimElement element) {
		this.stack.push(element);
	}

	@Override
	public void visitUnit(final Unit node) {
		MimPackage parent = getParentAs(MimPackage.class);
		MimPackage unitPackage = this.profile.createPackage(node.getIdentifier().getName());
		parent.addOwnedMember(unitPackage);
		push(unitPackage);
		super.visitUnit(node);
		pop(unitPackage);
	}

	@Override
	public void visitClassType(final ClassType node) {
		processClassifier(node);
	}

	@Override
	public void visitInterfaceType(final InterfaceType node) {
		MimClass type = processClassifier(node);
		if (type != null) {
			type.addStereotype(this.profile.getInterfaceStereotype());
		}
	}

	private MimClass processClassifier(final ClassifierDeclaration node) {
		if (!(node.getParent() instanceof TypeDeclaration)) {
			throw new RuntimeException(String.format(
					"%s is not subordinate to a type declaration.", node));
		}
		TypeDeclaration dec = (TypeDeclaration) node.getParent();
		MimPackage parent = getParentAs(MimPackage.class);
		String name = dec.getIdentifier().getName();
		MimClass type = parent.findOwnedMemberAs(name, MimClass.class);
		if (type == null) {
			type = this.profile.createClass(name);
			type.setOwner(parent);
		}
		type.setAbstract(node.isAbstract());
		parent.addOwnedMember(type);
		push(type);
		descend(node);
		pop(type);
		return type;
	}

	@Override
	public void visitMemberConstructorDeclaration(final MemberConstructorDeclaration node) {
		declareMethod(node);
		super.visitMemberConstructorDeclaration(node);
	}

	@Override
	public void visitMemberDestructorDeclaration(final MemberDestructorDeclaration node) {
		declareMethod(node);
		super.visitMemberDestructorDeclaration(node);
	}

	@Override
	public void visitMemberFunctionDeclaration(final MemberFunctionDeclaration node) {
		declareMethod(node);
		super.visitMemberFunctionDeclaration(node);
	}

	@Override
	public void visitMemberProcedureDeclaration(final MemberProcedureDeclaration node) {
		declareMethod(node);
		super.visitMemberProcedureDeclaration(node);
	}

	@Override
	public void visitMemberConstructorImplementation(final MemberConstructorImplementation node) {
		MimPackage pack = getFirstParentAs(MimPackage.class);
		try {
			QualifiedIdentifier methodIdent = node.getIdentifier();
			MimClass parent = (MimClass) pack.getOwnedMemberByName(methodIdent.getNamespaceName());
			Operation method = (Operation) parent.getOwnedMemberByName(methodIdent.getObjectName());
			push(method);
			super.visitMemberConstructorImplementation(node);
			pop(method);
		} catch (UnknownMemberException e) {
			throw new RuntimeException(String.format(
					"Failed to map method implementation %s due to: %s", node, e.getMessage()), e);
		}
	}

	@Override
	public void visitMemberDestructorImplementation(final MemberDestructorImplementation node) {
		MimPackage pack = getFirstParentAs(MimPackage.class);
		try {
			QualifiedIdentifier methodIdent = node.getIdentifier();
			MimClass parent = (MimClass) pack.getOwnedMemberByName(methodIdent.getNamespaceName());
			Operation method = (Operation) parent.getOwnedMemberByName(methodIdent.getObjectName());
			push(method);
			super.visitMemberDestructorImplementation(node);
			pop(method);
		} catch (UnknownMemberException e) {
			throw new RuntimeException(String.format(
					"Failed to map method implementation %s due to: %s", node, e.getMessage()), e);
		}
	}

	@Override
	public void visitMemberFunctionImplementation(final MemberFunctionImplementation node) {
		MimPackage pack = getFirstParentAs(MimPackage.class);
		try {
			QualifiedIdentifier methodIdent = node.getIdentifier();
			MimClass parent = (MimClass) pack.getOwnedMemberByName(methodIdent.getNamespaceName());
			Operation method = (Operation) parent.getOwnedMemberByName(methodIdent.getObjectName());
			push(method);
			super.visitMemberFunctionImplementation(node);
			pop(method);
		} catch (UnknownMemberException e) {
			throw new RuntimeException(String.format(
					"Failed to map method implementation %s due to: %s", node, e.getMessage()), e);
		}
	}

	@Override
	public void visitMemberProcedureImplementation(final MemberProcedureImplementation node) {
		MimPackage pack = getFirstParentAs(MimPackage.class);
		try {
			QualifiedIdentifier methodIdent = node.getIdentifier();
			MimClass parent = (MimClass) pack.getOwnedMemberByName(methodIdent.getNamespaceName());
			Operation method = (Operation) parent.getOwnedMemberByName(methodIdent.getObjectName());
			push(method);
			super.visitMemberProcedureImplementation(node);
			pop(method);
		} catch (UnknownMemberException e) {
			throw new RuntimeException(String.format(
					"Failed to map method implementation %s due to: %s", node, e.getMessage()), e);
		}
	}

	@Override
	public void visitAncestorDeclaration(final AncestorDeclaration node) {
		MimClass parent = getParentAs(MimClass.class);
		for (Identifier ancestorId : node.getAncestors()) {
			Classifier general = resolveClassReference(parent.getPackage(), ancestorId);
			Generalization gen = new Generalization();
			if (general instanceof StubClass) {
				((StubClass) general).addReferer(gen);
			}
			gen.setGeneral(general);
			gen.setSpecific(parent);
		}
	}

	@Override
	public void visitFieldDeclaration(final FieldDeclaration node) {
		MimClass parent = getParentAs(MimClass.class);
		VariableDeclaration var = node.getVariable();
		Type type = var.getType();
		for (Identifier ident : var.getIdentifiers()) {
			Property field = new Property();
			field.setName(ident.getName());
			field.setDefaultValue(representNode(var.getDefaultValue()));
			int upperBound = 1;
			if (type instanceof ArrayType) {
				type = ((ArrayType) type).getComponentType();
				upperBound = 0;
			}
			String typeName = representNode(type);
			MimType modelType = resolveType(typeName);
			field.setType(modelType);
			field.getMultiplicity().setUpper(upperBound);
			parent.addOwnedMember(field);
		}
	}

	/**
	 * Attempts to resolve the type for the given type name. The profile is
	 * searched first, followed by the package members. If the type is unknown,
	 * a type stub is created and registered with the package.
	 * 
	 * @param typeName
	 *            the name of the type to resolve.
	 * @return the resolved type.
	 */
	@Nonnull
	private MimType resolveType(final String typeName) {
		MimType result = this.profile.findType(typeName);
		if (result == null) {
			MimPackage parent = getFirstParentAs(MimPackage.class);
			result = parent.findType(typeName);
			if (result == null) {
				createStubType(parent, typeName, String.format(
						"Type %s is neither provided by the profile nor known to package %s.",
						typeName, parent.getName()));
			}
		}
		return result;
	}

	@Override
	public void visitInterfaceSection(final InterfaceSection node) {
		UsesSection usesSection = node.getUsesSection();
		if (usesSection != null) {
			MimPackage parent = getParentAs(MimPackage.class);
			for (Identifier importedIdentifier : usesSection.getImports()) {
				parent.addPackageImportStub(importedIdentifier.getName());
			}
		}
		super.visitInterfaceSection(node);
	}

	@Override
	public void visitIdentifier(final Identifier node) {
		Dependent parent = findFirstParentAs(Dependent.class);
		if (parent != null) {
			parent.addIdentifierDependency(node.getName());
		}
	}

	private String representNode(final Node node) {
		if (node == null) {
			return StringUtils.EMPTY;
		}
		return DelphiSyntaxWriter.writeToString(node);
	}

	/**
	 * Resolves the class with the given identifier within the given parent
	 * package. Creates a stub class in case the class is not known.
	 * 
	 * @param parent
	 * @param identifier
	 * @return
	 */
	private Classifier resolveClassReference(final MimPackage parent, final Identifier identifier) {
		String name = identifier.getName();
		try {
			NamedElement general = parent.getMemberByName(name);
			if (!(general instanceof Classifier)) {
				String message = String.format("Class %s generalizes non-classifier \"%s\".",
						general.getName(), name);
				// LOGGER.warning(message);
				general = createStubClass(parent, identifier, message);
			}
			return (Classifier) general;
		} catch (UnknownMemberException e) {
			String message = String.format(
					"Class %s generalizes \"%s\", which is not known to package \"%s\".",
					parent.getName(), name, parent.getName());
			// LOGGER.warning(message);
			return createStubClass(parent, identifier, message);
		}
	}

	private Classifier createStubClass(final MimPackage parent, final Identifier identifier,
			final String message) {
		StubClass stub = new StubClass();
		stub.setName(identifier.getName());
		stub.setStubReason(message);
		parent.addOwnedMember(stub);
		return stub;
	}

	private MimType createStubType(final MimPackage parent, final String typeName,
			final String format) {
		StubType stub = new StubType();
		stub.setName(typeName);
		parent.addOwnedMember(stub);
		return stub;
	}

	private void declareMethod(final MethodDeclaration node) {
		Classifier parent = getParentAs(Classifier.class);
		Operation op = new Operation();
		op.setName(node.getIdentifier().getName());
		parent.addOwnedMember(op);
	}

	@SuppressWarnings("unchecked")
	private <T> T getParentAs(final java.lang.Class<T> elementClass) {
		if (this.stack.isEmpty()) {
			throw new RuntimeException(
					"Cannot return the current parent because the element stack is empty.");
		}
		return (T) this.stack.get(this.stack.size() - 1);
	}

	/**
	 * Ascends the element stack until an element of the given type is found.
	 * 
	 * @param elementClass
	 *            the type to retrieve from the stack.
	 * @return
	 */
	private <T> T getFirstParentAs(final Class<T> elementClass) {
		T result = findFirstParentAs(elementClass);
		if (result == null) {
			throw new RuntimeException(String.format(
					"There is no element of type %s present on the stack.",
					elementClass.getSimpleName()));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private <T> T findFirstParentAs(final Class<T> elementClass) {
		for (int i = this.stack.size() - 1; i >= 0; i--) {
			MimElement cur = this.stack.get(i);
			if (elementClass.isAssignableFrom(cur.getClass())) {
				return (T) cur;
			}
		}
		return null;
	}

}
