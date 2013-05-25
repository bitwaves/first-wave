package ch.bitwave.lang.delphi.driver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import ch.bitwave.lang.delphi.ast.AncestorDeclaration;
import ch.bitwave.lang.delphi.ast.ClassType;
import ch.bitwave.lang.delphi.ast.Identifier;
import ch.bitwave.lang.delphi.ast.ImplementationSection;
import ch.bitwave.lang.delphi.ast.InterfaceSection;
import ch.bitwave.lang.delphi.ast.MemberFunctionImplementation;
import ch.bitwave.lang.delphi.ast.MemberProcedureImplementation;
import ch.bitwave.lang.delphi.ast.Node;
import ch.bitwave.lang.delphi.ast.ParameterDeclaration;
import ch.bitwave.lang.delphi.ast.ParameterModifierKind;
import ch.bitwave.lang.delphi.ast.Type;
import ch.bitwave.lang.delphi.ast.TypeDeclaration;
import ch.bitwave.lang.delphi.ast.TypeSection;
import ch.bitwave.lang.delphi.ast.Unit;
import ch.bitwave.lang.delphi.ast.UsesSection;
import ch.bitwave.lang.delphi.testbase.Resources;

public class DelphiSourceParserDriverTest {

	@Test
	public void shouldMapSampleUnit5() throws IOException {
		Unit unit = parseLatin1("SampleUnit5.pas");

		// Unit
		assertEquals("SampleUnit5", unit.getIdentifier().getName());

		{
			// Interface section
			InterfaceSection interfaceSection = unit.getInterfaceSection();
			assertNotNull(interfaceSection);
			assertEquals(2, interfaceSection.getDetailCount());
			UsesSection uses = interfaceSection.getUsesSection();
			assertNotNull(uses);
			List<Identifier> imports = uses.getImports();
			assertEquals(6, imports.size());
			assertEquals("SampleUnit2", imports.get(0).getName());
			assertEquals("bu_ControlAbstractionTypes", imports.get(1).getName());
			assertEquals("u_BedrockInterfaces", imports.get(5).getName());
			List<TypeSection> typeSections = interfaceSection.getTypeSections();
			assertEquals(1, typeSections.size());
			List<TypeDeclaration> types = typeSections.get(0).getDeclaredTypes();
			assertEquals(1, types.size());
			{
				// Class declaration of TBPArrowPopupButton
				TypeDeclaration decl = types.get(0);
				ClassType classType = (ClassType) decl.getType();
				assertEquals("TBPArrowPopupButton", decl.getIdentifier().getName());
				AncestorDeclaration pebbleAncestors = classType.getAncestorDeclaration();
				List<Identifier> idents = pebbleAncestors.getAncestors();
				assertEquals(2, idents.size());
				assertEquals("TBPPopupButton", idents.get(0).getName());
				assertEquals("IBPArrowPopupButton", idents.get(1).getName());
			}
		}
		{
			// Implementation section
			ImplementationSection implementationSection = unit.getImplementationSection();
			assertNotNull(implementationSection);
			UsesSection uses = implementationSection.getUsesSection();
			assertNotNull(uses);
			List<Identifier> imports = uses.getImports();
			assertEquals(1, imports.size());
			assertEquals("dbu_ToolboxUtils", imports.get(0).getName());
			List<Node> implDetails = implementationSection.getDetails();
			assertEquals(3, implDetails.size());
			{
				// Method impl TBPArrowPopupButton.GetGUID
				MemberFunctionImplementation impl = (MemberFunctionImplementation) implDetails
						.get(1);
				assertEquals("TBPArrowPopupButton.GetGUID", impl.getIdentifier().getName());
				List<ParameterDeclaration> params = impl.getParameters();
				assertEquals(0, params.size());
			}
			{
				// Method impl TBPArrowPopupButton.InternalLayout
				MemberProcedureImplementation impl = (MemberProcedureImplementation) implDetails
						.get(2);
				assertEquals("TBPArrowPopupButton.InternalLayout", impl.getIdentifier().getName());
				List<ParameterDeclaration> params = impl.getParameters();
				assertEquals(8, params.size());
				assertParameter(ParameterModifierKind.CONST, "context", params.get(0));
				assertParameter(ParameterModifierKind.VAR, "area", params.get(2));
				assertParameter(ParameterModifierKind.CONST, "clip", params.get(3));
				assertParameter(ParameterModifierKind.CONST, "style", params.get(4));
				assertParameter(ParameterModifierKind.CONST, "strPath", params.get(5));
				assertParameter(ParameterModifierKind.CONST, "payload", params.get(6));
				assertParameter(ParameterModifierKind.CONST, "openEdges", params.get(7));
			}
		}
	}

	private Type assertParameter(final ParameterModifierKind modifier,
			final String identifierNames, final ParameterDeclaration param) {
		assertEquals(modifier, param.getModifier());
		assertEquals(identifierNames, param.getIdentifierNames());
		assertNotNull(param.getType());
		return param.getType();
	}

	private Unit parseLatin1(final String fileName) throws IOException {
		return new DelphiSourceParserDriver().readFile(getFile(fileName), Resources.ENCODING);
	}

	private File getFile(final String fileName) {
		return new File(Resources.RESOURCE_FOLDER + fileName);
	}
}
