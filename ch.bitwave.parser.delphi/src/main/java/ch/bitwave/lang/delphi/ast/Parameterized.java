package ch.bitwave.lang.delphi.ast;

import java.util.List;

public interface Parameterized {

	void addParameter(ParameterDeclaration decl);

	List<ParameterDeclaration> getParameters();

	boolean hasParameters();

}
