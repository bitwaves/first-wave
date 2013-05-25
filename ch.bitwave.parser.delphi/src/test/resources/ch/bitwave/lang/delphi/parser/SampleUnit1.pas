unit SampleUnit;

interface

const
  // This constant is entirely unused.
  gcstrSingleQuote = '''';

type

  { This is an interface to a very strange class }
  IFoo = interface
    ['{E09D5BDD-7E19-472C-9C87-A00F97291C5E}']
    function GetBar: string;
    procedure DoMagic(const a, b, c: String; out pixieDust: IInterface);
  end;

  TIncompleteClass = class(IFoo);

  TFooBarClass = class of TFooBar;

  { This class does many strange things }
  TFooBar = class(TInterfacedObject, IFoo)
    FintIncrement: integer;
    FItems: array of IcaMenuItem;
  protected
    // Helper methods
  protected
    // IFoo
    function GetBar: string;
    procedure DoMagic(const a, b, c: String; out pixieDust: IInterface); virtual;
    function GetPixieDust: IInterface; abstract;
    function GetItemByIndex(const intIndex: integer): IcaMenuItem;
    procedure RegisterItem(const item: IcaMenuItem);
  public
    constructor Create;
    destructor Destroy;
   published
    property Increment: integer read FintIncrement write FintIncrement;
  end;
  
function GetTitleForCatalogItemClassic(const strCatalogCode, strItemCode: string): string; overload;
function GetTitleForCatalogItemClassic(const strCatalogCode, strItemCode: string; var blnSuccess: boolean):
  string; overload;

implementation

uses
  SysUtils;


function GetTitleForCatalogItemClassic(const strCatalogCode, strItemCode: string; var blnSuccess: boolean):
  string;
  function GetTitelOfDeletedItem(const strCatalogCode, strItemCode: string; var blnSuccess: boolean): string;
  var
    ds: IdaDataset;
    sql: string;
  begin
    blnSuccess:= false;
    result:= '';
    ds:= gKernel.GetAdapter.GetConnection.CreateDataset;
    sql:= Format('select Text from APPSTAMM where stamm = %s and code = %s',
      [Quote(strCatalogCode),
      Quote(strItemCode)]
        );
    ds.SetCommandText(sql);
    ds.Open;
    if ds.HasRecord then begin
      blnSuccess:= true;
      result:= ForceVarToString(ds.GetFieldValueByName('Text'));
    end;
  end;
begin
  blnSuccess:= true;
  if strCatalogCode = '' then begin
    blnSuccess:= false;
    result:= Format('<catalog not set for item "%s">', [strItemCode]);
  end
  else begin
    try
      result:= gKernel.GetCatalogClassicController.GetCatalog(strCatalogCode).GetSingleItemTitle(strItemCode);
    except
      // Handle this very important issue.
      on e: EBLOBCacheInconsistentDataException do begin
        raise ExceptionChain(e,
          cstrUnit + '.GetTitleForCatalogItemClassic',
          'Failed to load catalog due to data inconsistencies!'
          );
      end
    else begin
        // Do something else.
        try
          gKernel.GetCatalogClassicController.GetCatalog(strCatalogCode).Load(true {blnCheckTimeStamp});
          result:=
            gKernel.GetCatalogClassicController.GetCatalog(strCatalogCode).GetSingleItemTitle(strItemCode);
        except
          result:= GetTitelOfDeletedItem(strCatalogCode, strItemCode, blnSuccess);
          if not blnSuccess then
            result:= Format('<Item %s not available in catalog %s>', [strItemCode, strCatalogCode]);
        end;
      end;
    end;
  end;
end;

function GetTitleForCatalogItemClassic(const strCatalogCode, strItemCode: string): string;
var
  blnDummy: boolean;
begin
  Result:= GetTitleForCatalogItemClassic(strCatalogCode, strItemCode, blnDummy);
end;

function MapCatalogItemBitmap(const intItemIcon, intItemState: integer): string;
begin
  case intItemIcon of
    0: begin
        case intItemState of
          integer(cisReadyToPublish): result:= cstrGIFServiceReadyToPublish;
          integer(cisPublished): result:= cstrGIFServicePublished;
          integer(cisReadyToRetire): result:= cstrGIFServiceReadyToRetire;
          integer(cisRetired): result:= cstrGIFServiceRetired;
        else
          result:= cstrGIFService;
        end;
      end;
    1: begin
        case intItemState of
          integer(cisReadyToPublish): result:= cstrGIFMedicalServiceReadyToPublish;
          integer(cisPublished): result:= cstrGIFMedicalServicePublished;
          integer(cisReadyToRetire): result:= cstrGIFMedicalServiceReadyToRetire;
          integer(cisRetired): result:= cstrGIFMedicalServiceRetired;
        else
          result:= cstrGIFMedicalService;
        end;
      end;
  else begin
      case intItemState of
        integer(cisReadyToPublish): result:= cstrGIFServiceReadyToPublish;
        integer(cisPublished): result:= cstrGIFServicePublished;
        integer(cisReadyToRetire): result:= cstrGIFServiceReadyToRetire;
        integer(cisRetired): result:= cstrGIFServiceRetired;
      else
        result:= cstrGIFService;
      end;
    end;
  end;
end;

{ TFooBar }

constructor TFooBar.Create;
begin
  FintIncrement:= 2;
end;

destructor TFooBar.Destroy;
begin

end;

procedure TFooBar.DoMagic(const a, b, c: String; out pixieDust: IInterface);
var
  x: integer = 0;
begin
  repeat
    Inc(x, 2);
    Dec(x);
  until x>100;
  while x>0 do begin
    x:= x-1;
  end;
  if (x>0) then
    raise Exception.Create('Illegal value')
  else
    pixieDust:= GetPixieDust;
end;

function TFooBar.GetBar: string;
var
  os: dbu_PhoenixInterfaces.IObjectSelection;
begin
  result:= 'Bar';
end;

function TFooBar.GetItemByIndex(const intIndex: integer): IcaMenuItem;
begin
  result:= FItems[intIndex];
end;

procedure TFooBar.RegisterItem(const item: IcaMenuItem);
var
  locEntry: IescLocator;
  eelEffectiveContext: IescExpressionElementList;

  procedure ConsiderAlpha(const locator: IescLocator; const pathEntry: IPathEntry); forward;
  procedure ConsiderBeta(const locator: IescLocator; const pathEntry: IPathEntry); forward;
begin
  try
    Inc(FintLastItemIndex);
    if FintLastItemIndex > High(FItems) then
      SetLength(FItems, FintLastItemIndex * 2);
    FItems[FintLastItemIndex]:= item;
  except
    on e: Exception do begin
      report.CreateError(Format(T('Foo failed. Exception occured: %s'), [e.Message]),
        nil);
      raise ExceptionChain(e,
        cstrUnit + ClassName + '.Do_Foo',
        'SubstituteItemsInMasters failed',
        ETemporaryScriptError
        );
    end;
  end;
end;

end.
