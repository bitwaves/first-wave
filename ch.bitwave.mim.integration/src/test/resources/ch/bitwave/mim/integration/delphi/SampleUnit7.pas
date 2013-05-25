{*
 *}
unit SampleUnit7;

interface

uses
  Classes,
  Graphics,
  Windows,
  Forms,
  SampleUnit3,
  SysUtils;

const
  cstrGUID_IHuman = '{82F52648-B94D-495C-B0A6-8BD4BF904846}';
  cstrGUID_IClientLanguage = '{6EA63F22-6809-4E85-960C-154421491506}';
  cstrGUID_IPerson = '{06B54C9E-6C85-4075-BDED-85406083648D}';
  cstrGUID_IDogDefinition = '{364A99BE-C420-4CA1-AA0C-0CA9C0568B59}';

type

  IHuman = interface
    [cstrGUID_IHuman]
    function FormatProperties(const strProperties: string = '<titeltext> <vorname> <name>'; const
      blnRespectLanguageOrder: boolean = false): string;
    function GetAnrede: integer;
    function GetAnredeText: string;
    function GetExists: boolean;
    function GetName: string;
    function GetPropertyCount: integer;
    function GetPropertyValue(const strName: string): string;
    function GetTitel: integer;
    function GetTitelText: string;
    function GetVorname: string;
    function GetUID: integer; 
    function GetAsText: string;
    procedure SetFromText(const strText: string);
    procedure SetPropertyValue(const strName, strValue: string);
  end;

  IPerson = interface(IHuman)
    [cstrGUID_IPerson]
    function GetGroupID: integer;
    function GetKSTelle: string;
    function GetPersID: integer;
    function GetVisum: string;
    function GetID: integer;
    procedure SetID(const intID: integer);
    procedure SetTitleCode(const intTitleCode: integer);
    procedure SetFirstname(const strFirstName: string);
    procedure SetLastname(const strLastName: string);
    procedure SetGroupID(const intID: integer);
  end;

  IClientLanguage = interface
    [cstrGUID_IClientLanguage]
    function GetAvailable: boolean;
    function GetIsSystemLanguage(out language: ISystemLanguage): boolean;
    function GetCode: string;
    function GetLabel: string;
    function GetRegionCode: integer;
  end;
  
  IDogDefinition = interface(IStorableDefinition)
    [cstrGUID_IDogDefinition]
    function CreateDogDefinitionField: IDogDefinitionField;
    function GetDogDefinitionField(const index: integer): IDogDefinitionField;
    function GetDogDefinitionFieldCount: integer;
    function GetDogDefinitionFieldIndex(const strName: string): integer;
    function GetGroupField: string;
    function GetName: string;
    procedure AddDogDefinitionField(const aField: IDogDefinitionField);
    procedure RemoveDogDefinitionField(const aField: IDogDefinitionField); overload;
    procedure RemoveDogDefinitionField(const index: integer); overload;
    procedure SetGroupField(const strValue: string);
    procedure SetName(const strValue: string);
  end;

implementation

end.

