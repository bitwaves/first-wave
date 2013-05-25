{*
 *}
unit SampleUnit4;

interface

uses
  ADODB,
  Classes,
  d_ADOOperation,
  d_DatabaseAbstractionTypes,
  DB,
  dbu_DatabaseAbstraction,
  SampleUnit3,
  Variants;

type
  TDataset = class(TOperation, IdaDataset, IdaDatasetFriend, ISATableData)
  protected
    Fds: TADODataset;
    FslFields: IStringInterfaceList; 
    FslFieldIndex: IStringInterfaceList;
    FStopWatch: IStopWatch;
  protected
    function FindFieldByIndex(const intIndex: integer; out field: IdaField): boolean;
    function GetCommandText: string; override;
    function InternalEof: boolean;
    procedure EnsureParameters; override;
    procedure InternalClose;
    procedure InternalNext;
    procedure ReconstructFieldIndex;
    procedure SetCommandText(const strText: string); override;
    procedure SetCommand(const statement: IdaParameterizedStatement); override;
    procedure SetFieldValueByIndex(const intIndex: integer; const Value: Variant);
    procedure SetFieldValueByName(const strName: string; const Value: Variant);
  protected
    // IdaDataset
    function FindField(const strName: string; out field: IdaField): boolean;
    function CloneDataset: IdaDataset;
    function GetCursorLocation: TdaCursorLocation;
    function GetFieldByIndex(const intIndex: integer): IdaField;
    function GetFieldByName(const strName: string): IdaField;
    function GetFieldCount: integer;
    function GetFieldIndexByName(const strFieldName: string): integer;
    function GetFieldValueByIndex(const intIndex: integer): Variant;
    function GetFieldValueByName(const strName: string): Variant;
    function GetLastFieldIndex: integer;
    function GetName: string;
    function GetTag: integer;
    function HasField(const strName: string): boolean;
    function HasRecord: boolean;
    function IsActive: boolean;
    function Open: boolean;
    procedure Close;
    procedure DisableControls;
    procedure EnableControls;
    procedure GetFieldNames(const fieldNames: IStringList);
    procedure Next;
    procedure Reopen;
    procedure SetName(const strValue: string);
    procedure SetTag(const Value: integer);
    // IdaDatasetFriend
    function EnsureColumn(const intColumnIndex: integer; const strTitle: string; const enumType: TdaDataType):
      IdaColumn;
    procedure EnsureFields;
    procedure SetCursorLocation(const enumLocation: TdaCursorLocation);
    // ISATableData
    function EOF_STD: boolean;
    function GetLastColumnIndex_STD: integer;
    function GetTitleByColumnIndex_STD(const intColumnIndex: integer): string;
    function GetTypeByColumnIndex_STD(const intColumnIndex: integer): string;
    function GetValueByColumnIndex_STD(const intColumnIndex: integer): variant;
    procedure Next_STD;
  public
    constructor Create(const connection: IdaConnectionFriend; const ds: TADODataset); reintroduce; virtual;
    destructor Destroy; override;
  end;

var
  gintIC_TdaADODataset: integer;

implementation

uses
  d_ADOField,
  d_Column,
  d_DatabaseAbstractionUtils,
  dbu_Exceptions,
  dbu_Lists,
  dbu_LowUtils,
  dbu_StopWatch,
  Math,
  SysUtils,
  WideStrings,
  d_ADOTypes;

const
  cstrUnit = 'SampleUnit4.';

  { TDataset }

constructor TDataset.Create(const connection: IdaConnectionFriend; const ds: TADODataset);
begin
  inherited Create(connection);
  FConnection:= connection;
  Fds:= ds;
  FStopWatch:=TStopWatch.Create;
  InterlockedIncrement(gintIC_TdaADODataset);
end;

destructor TDataset.Destroy;
begin
  inherited;
  FreeAndNil(Fds);
  InterlockedDecrement(gintIC_TdaADODataset);
end;

procedure TDataset.DisableControls;
begin
  Fds.DisableControls;
end;

procedure TDataset.EnsureFields;
var
  i: integer;
  field: IdaField;
begin
  try
    if not Assigned(FslFields) then begin
      FslFields:= GetListFactory.CreateStringInterfaceList;
      FslFields.SetSorted(false);
      FslFields.SetCaseSensitive(false);
      FslFields.SetDuplicates(dupError);
      FslFieldIndex:= GetListFactory.CreateStringInterfaceList;
      FslFieldIndex.SetSorted(true);
      FslFieldIndex.SetCaseSensitive(false);
      FslFieldIndex.SetDuplicates(dupError);
      for i:= 0 to Fds.FieldCount - 1 do begin
        field:= TdaADOField.Create(Fds.Fields[i]);
        FslFields.AddInterface(field.GetName, field);
        FslFieldIndex.AddInterface(field.GetName, field);
      end;
    end;
  except
    raise ExceptionPrepare(
      cstrUnit + ClassName + '.EnsureFields',
      'Failed to ensure field objects'
      );
  end;
end;

function TDataset.GetCommandText: string;
begin
  result:= Fds.CommandText;
end;

procedure TDataset.SetCommand(const statement: IdaParameterizedStatement);
begin
  InternalClose;
  ClearParameters;
  Fds.CommandText:= statement.GetText;
  ApplyParameterList(statement.GetParameters);
end;

procedure TDataset.SetCommandText(const strText: string);
begin
  if (strText <> Fds.CommandText) then begin
    InternalClose;
    Fds.CommandText:= strText;
  end;
end;

procedure TDataset.SetCursorLocation(
  const enumLocation: TdaCursorLocation);
begin
  case enumLocation of
    daclServerSide: Fds.CursorLocation:= clUseServer;
    daclClientSide: Fds.CursorLocation:= clUseClient;
  else begin
      raise ExceptionPrepare(
        cstrUnit + ClassName + '.SetCursorLocation',
        'Unsupported cursor location'
        );
    end;
  end;
end;

procedure TDataset.SetFieldValueByIndex(
  const intIndex: integer;
  const Value: Variant);
begin
  Fds.Fields[intIndex].Value:= Value;
end;

procedure TDataset.SetFieldValueByName(
  const strName: string;
  const Value: Variant);
begin
  Fds.FieldByName(strName).Value:= Value;
end;

procedure TDataset.EnableControls;
begin
  Fds.EnableControls;
end;

function TDataset.EnsureColumn(
  const intColumnIndex: integer;
  const strTitle: string;
  const enumType: TdaDataType): IdaColumn;
var
  daField: IdaField;
begin
  EnsureFields;
  while FslFields.GetLastIndex < intColumnIndex do begin
    daField:= TdaADOField.Create(nil);
    FslFields.AddInterface('', daField);
  end;
  daField:= FslFields.GetInterface(intColumnIndex) as IdaField;
  FslFields.Put(intColumnIndex, strTitle);
  (daField as IdaFieldFriend).SetFieldType(enumType);
  (daField as IdaFieldFriend).SetFieldName(strTitle);
  ReconstructFieldIndex;
  result:= daField.GetColumn;
  if not Assigned(result) then begin
    case daField.GetType of
      dadtString: result:= TStringColumn.Create;
      dadtInteger: result:= TIntegerColumn.Create;
      dadtFloat: result:= TFloatColumn.Create;
      dadtMemo: result:= TStringColumn.Create;
      dadtBoolean: result:= TIntegerColumn.Create;
      dadtDate, dadtTime, dadtDateTime: result:= TDateTimeColumn.Create;
      dadtBytes, dadtVarBytes, dadtBlob: result:= TBinaryColumn.Create;
    else 
      raise ExceptionPrepare(
        cstrUnit + ClassName + '.EnsureColumn',
        Format('Failed to provide a column for field "%s" of type %s', [daField.GetName,
        AbstractDataTypeToString(daField.GetType)])
          );
    end;
    (daField as IdaFieldFriend).SetColumn(result);
  end;
end;

procedure TDataset.ReconstructFieldIndex;
var
  i: integer;
begin
  if not Assigned(FslFieldIndex) then begin
    FslFieldIndex:= GetListFactory.CreateStringInterfaceList;
    FslFieldIndex.SetSorted(true);
    FslFieldIndex.SetCaseSensitive(false);
    FslFieldIndex.SetDuplicates(dupError);
  end
  else
    FslFieldIndex.Clear;
  for i:= 0 to FslFields.GetLastIndex do
    FslFieldIndex.AddInterface(FslFields.Get(i), FslFields.GetInterface(i));
end;

procedure TDataset.EnsureParameters;
begin
  if not Assigned(FParameters) then
    FParameters:= TParameters.Create(Fds.Parameters.Command, TParameter);
end;

function TDataset.EOF_STD: boolean;
begin
  result:= InternalEof;
end;

function TDataset.GetLastColumnIndex_STD: integer;
begin
  result:= FslFields.GetLastIndex;
end;

function TDataset.GetTitleByColumnIndex_STD(
  const intColumnIndex: integer): string;
var
  field: IdaField;
begin
  if not FindFieldByIndex(intColumnIndex, field) then
    raise ExceptionPrepare_IndexOutOfRange(
      cstrUnit + ClassName + '.GetTitleByColumnIndex_STD',
      intColumnIndex
      );
  result:= field.GetName;
end;

function TDataset.GetTypeByColumnIndex_STD(
  const intColumnIndex: integer): string;
var
  field: IdaField;
begin
  if not FindFieldByIndex(intColumnIndex, field) then
    raise ExceptionPrepare_IndexOutOfRange(
      cstrUnit + ClassName + '.GetTypeByColumnIndex_STD',
      intColumnIndex
      );
  result:= AbstractDataTypeToString(field.GetType);
end;

function TDataset.GetValueByColumnIndex_STD(
  const intColumnIndex: integer): variant;
var
  field: IdaField;
begin
  if not FindFieldByIndex(intColumnIndex, field) then
    raise ExceptionPrepare_IndexOutOfRange(
      cstrUnit + ClassName + '.GetValueByColumnIndex_STD',
      intColumnIndex
      );
  result:= field.GetValue;
end;

procedure TDataset.Next_STD;
begin
  InternalNext;
end;

function TDataset.FindField(
  const strName: string;
  out field: IdaField): boolean;
var
  intIndex: integer;
begin
  EnsureFields;
  intIndex:= FslFieldIndex.IndexOf(strName);
  if intIndex >= 0 then
    field:= FslFieldIndex.GetInterface(intIndex) as IdaField;
  result:= Assigned(field);
end;

function TDataset.FindFieldByIndex(
  const intIndex: integer;
  out field: IdaField): boolean;
begin
  EnsureFields;
  if InRange(intIndex, 0, FslFields.GetLastIndex) then begin
    field:= FslFields.GetInterface(intIndex) as IdaField;
    result:= true;
  end
  else
    result:= false;
end;

function TDataset.InternalEof: boolean;
begin
  result:= Fds.Eof;
end;

procedure TDataset.InternalNext;
begin
  Fds.Next;
end;

procedure TDataset.InternalClose;
begin
  if Fds.Active then
    Fds.Close;
  FslFields:= nil;
  FslFieldIndex:= nil;
end;

function TDataset.CloneDataset: IdaDataset;
var
  ds: TADODataset;
begin
  ds:= TADODataset.Create(nil);
  if Fds.FieldCount > 0 then
    ds.Clone(Fds);
  result:= TDataset.Create(FConnection, ds);
end;

procedure TDataset.Close;
begin
  InternalClose;
end;

function TDataset.GetCursorLocation: TdaCursorLocation;
begin
  case Fds.CursorLocation of
    clUseServer: result:= daclServerSide;
    clUseClient: result:= daclClientSide;
  else 
    raise ExceptionPrepare(
      cstrUnit + ClassName + '.GetCursorLocation',
      'Unsupported cursor location'
      );
  end;
end;

function TDataset.GetFieldByIndex(const intIndex: integer): IdaField;
begin
  if not FindFieldByIndex(intIndex, result) then
    raise ExceptionPrepare_IndexOutOfRange(
      cstrUnit + ClassName + '.GetFieldByIndex',
      intIndex
      );
end;

function TDataset.GetFieldByName(const strName: string): IdaField;
var
  intIndex: integer;
begin
  EnsureFields;
  intIndex:= FslFieldIndex.IndexOf(strName);
  if intIndex < 0 then begin
    raise ExceptionPrepare(
      cstrUnit + ClassName + '.GetFieldByName',
      Format('Field "%s" does not exist', [strName])
      );
  end;
  result:= FslFieldIndex.GetInterface(intIndex) as IdaField;
end;

function TDataset.GetFieldCount: integer;
begin
  result:= Fds.FieldCount;
end;

function TDataset.GetFieldIndexByName(
  const strFieldName: string): integer;
var
  intIndex: integer;
  i: integer;
  field: IdaField;
begin
  if Assigned(FslFieldIndex) then begin
    intIndex:= FslFieldIndex.IndexOf(strFieldName);
    result:= -1;
    if intIndex >= 0 then begin
      field:= FslFieldIndex.GetInterface(intIndex) as IdaField;
      for i:= 0 to FslFields.GetLastIndex do begin
        if (FslFields.GetInterface(i) as IdaField) = field then begin
          result:= i;
          break;
        end;
      end;
    end;
  end
  else
    result:= Fds.FieldDefs.IndexOf(strFieldName);
end;

procedure TDataset.GetFieldNames(const fieldNames: IStringList);
var
  list: TWideStrings;
begin
  try
    list:= TWideStringList.Create;
    Fds.GetFieldNames(list);
    fieldNames.SetText(list.Text);
  finally
    FreeAndNil(list);
  end;
end;

function TDataset.GetFieldValueByIndex(
  const intIndex: integer): Variant;
begin
  if Fds.Fields[intIndex] is TBlobField then begin
    result:= (Fds.Fields[intIndex] as TBlobField).Value;
  end
  else begin
    result:= null;
    result:= Fds.Fields[intIndex].Value;
  end;
end;

function TDataset.GetFieldValueByName(const strName: string): Variant;
var
  field: TField;
begin
  field:= Fds.FieldByName(strName);
  result:= GetFieldValueByIndex(field.Index);
end;

function TDataset.GetLastFieldIndex: integer;
begin
  if Assigned(FslFields) then
    result:= FslFields.GetLastIndex
  else
    result:= Fds.Fields.Count - 1;
end;

function TDataset.GetName: string;
begin
  result:= Fds.Name;
end;

function TDataset.GetTag: integer;
begin
  result:= Fds.Tag;
end;

function TDataset.HasField(const strName: string): boolean;
var
  intIndex: integer;
begin
  EnsureFields;
  intIndex:= FslFieldIndex.IndexOf(strName);
  result:= intIndex >= 0;
end;

function TDataset.HasRecord: boolean;
begin
  result:= not InternalEof;
end;

function TDataset.IsActive: boolean;
begin
  result:= Fds.Active;
end;

procedure TDataset.Next;
begin
  InternalNext;
end;

function TDataset.Open: boolean;
var
  ne: Exception;
  acf: IADOConnectionFriend;
  monitor: IdaConnectionMonitor;
  strText: string;
begin
  try
    if Length(Fds.CommandText) > cADOSqlMaxLength then begin
      ne:= ExceptionPrepare(
        cstrUnit + ClassName + '.Open',
        'SQL statement is too long',
        EPreventiveDatabaseError
        );
      ExceptionMessageAddAttribute(ne, 'Supported size', cADOSqlMaxLength);
      ExceptionMessageAddAttribute(ne, 'Offending statement [truncated]', Copy(Fds.CommandText, 1, 150));
      raise ne;
    end;
    InternalClose;
    if HasParameters then
      Fds.Parameters.Assign(FParameters);
    if GetMonitor(monitor) then begin
      strText:= WritePreExecuteNotification;
      monitor.PreExecuteNotification(FConnection, strText);
    end;
    FStopWatch.Start;
    try
      Fds.Open;
      result:= not Fds.Eof;
    finally
      if GetMonitor(monitor) then
        monitor.PostExecuteNotification(FConnection, strText,FStopWatch.StopAndGetSeconds);
    end;
  except
    on e: Exception do begin
      ne:= ExceptionChain(e,
        cstrUnit + ClassName + '.Open',
        'Failed to open dataset'
        );
      ExceptionMessageAddAttribute(ne, 'SQL', Fds.CommandText);

      if Supports(self.GetConnection,IADOConnectionFriend,acf) then
        ExceptionMessageAddAttribute(ne, 'Errors',acf.GetAllADOErrors);

      raise ne;
    end;
  end;
end;

procedure TDataset.Reopen;
begin
  Close;
  Open;
end;

procedure TDataset.SetName(const strValue: string);
begin
  Fds.Name:= strValue;
end;

procedure TDataset.SetTag(const Value: integer);
begin
  Fds.Tag:= Value;
end;


end.
