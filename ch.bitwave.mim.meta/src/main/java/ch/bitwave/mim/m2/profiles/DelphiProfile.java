package ch.bitwave.mim.m2.profiles;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.commons.lang.StringUtils;

import ch.bitwave.mim.m2.core.MetaModelRuntimeException;
import ch.bitwave.mim.m2.core.MimClass;
import ch.bitwave.mim.m2.core.MimPackage;
import ch.bitwave.mim.m2.core.MimType;
import ch.bitwave.mim.m2.core.PrimitiveType;
import ch.bitwave.mim.m2.core.Profile;
import ch.bitwave.mim.m2.core.Stereotype;

public class DelphiProfile extends Profile {

	private final Stereotype interfaceStereotype;
	private final MimType stringType;
	private final MimType integerType;
	private final MimType booleanType;

	Map<String, MimType> typesByName;

	public DelphiProfile() {
		this.typesByName = new HashMap<String, MimType>();
		this.interfaceStereotype = createStereotype("interface");
		this.stringType = createPrimitiveType("string");
		this.integerType = createPrimitiveType("integer");
		this.booleanType = createPrimitiveType("boolean");
	}

	private MimType createPrimitiveType(final String name) {
		PrimitiveType result = new PrimitiveType();
		result.setName(name);
		this.typesByName.put(name, result);
		return result;
	}

	private Stereotype createStereotype(final String name) {
		Stereotype result = new Stereotype();
		result.setName(name);
		addOwnedMember(result);
		this.typesByName.put(name, result);
		return result;
	}

	public Stereotype getInterfaceStereotype() {
		return this.interfaceStereotype;
	}

	public MimType getStringType() {
		return this.stringType;
	}

	public MimType getIntegerType() {
		return this.integerType;
	}

	public MimType getBooleanType() {
		return this.booleanType;
	}

	public MimType getType(@Nonnull final String typeName) {
		MimType result = findType(typeName);
		if (result == null) {
			throw new MetaModelRuntimeException(String.format(
					"The Delphi profile does not provide type \"%s\".", typeName));
		}
		return result;
	}

	@Override
	public MimType findType(final String typeName) {
		return this.typesByName.get(typeName.toLowerCase());
	}

	public MimPackage createPackage(final String name) {
		MimPackage result = new MimPackage();
		result.setNamingStrategy(this);
		result.setName(name);
		return result;
	}

	public MimClass createClass(final String name) {
		MimClass result = new MimClass();
		result.setName(name);
		result.setNamingStrategy(this);
		return result;
	}

	@Override
	public boolean isSameName(final String firstName, final String secondName) {
		return StringUtils.equalsIgnoreCase(firstName, secondName);
	}

}
