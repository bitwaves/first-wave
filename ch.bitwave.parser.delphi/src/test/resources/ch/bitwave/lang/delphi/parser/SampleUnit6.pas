{*
 *}
unit SampleUnit6;

interface

uses
  b_AbstractController,
  b_ControllerInterfaces,
  b_ControllerTypes,
  b_BusinessInterfaces,
  bu_ControlAbstractionTypes,
  bu_ControlAbstractionInterfaces,
  SampleUnit3;

type

  {*
   *}
  TBusinessClass = class(
      TAbstractController,
      IBusinessController)
  protected
    function DescribeCatalogLocator(const desc: IGraphicsDescription; const locEntry: IescLocator; const
      payloadPath: IPayloadPath): string; virtual;
    function GetLabelPath(const eelPath: IescExpressionElementList): string;
    function GetLeaf(const payload: IInterface; out leaf: IInterface): boolean;
    function GetObject(const locObj: IescLocator; const IID: TGUID; out AInterface): boolean; virtual;
    function GetSession: IBusinessSession;
    function GetUserObject: IpoUser;
    function GetUser: IPhoenixUser;
    function KeyToHintParagraph(const strKey: string): string;
    function MayAdmin(const element: ICatalogElement): boolean; overload;
    function MayAdmin(const securedObject: IpoSecuredObject): boolean; overload;
    function MayExecute(const element: ICatalogElement): boolean; overload;
    function MayExecute(const securedObject: IpoSecuredObject): boolean; overload;
    function MayRead(const element: ICatalogElement): boolean; overload;
    function MayRead(const securedObject: IpoSecuredObject): boolean; overload;
    function MayWrite(const element: ICatalogElement): boolean; overload;
    function MayWrite(const securedObject: IpoSecuredObject): boolean; overload;
    function MayAdmin(const acl: IAccessControlList): boolean; overload;
    function MayExecute(const acl: IAccessControlList): boolean; overload;
    function MayRead(const acl: IAccessControlList): boolean; overload;
    function MayWrite(const acl: IAccessControlList): boolean; overload;
    procedure DispatchMessage(const intMessageType: integer); overload;
    deprecated;
    procedure DispatchMessage(const msg: IMessage); overload;
    deprecated;
    function ResolvePatientByCode(const intPatientCode: integer): IpoPatient;
    function ResolveActByUID(const intActUID: integer): IpoAct;
    function ResolveParameterDefinition(const controller: IAbstractController; const strCode: string; out paramDef:
      IParameterDefinition): boolean;
    function GetColumnWidthSettingIdentifier(const binding: IcabBinding): string;
    function DoGetInitialColumnWidth(const binding: IcabBinding): double;
    procedure DoAdjustColumnWidth(const binding: IcabBinding; var realWidth: double);
    function ConvertTextToHint(const strText: string): string; override;
    function DoGetCode(const binding: IcabBinding; const payload: IInterface): string; override;
    function ExecuteRuleActionRequest(const ruleResponse: IescRuleResponse): boolean; override;
    function GetBoundInterface(const IID: TGUID; out AInterface): boolean; override;
    function GetBoundObject(out obj: IescObject): boolean; override;
    function GetCustomText(const binding: IcabBinding; const payload, contextPayload: IInterface; const
      strFacet: string): string; override;
    function GetObjectRepresentation_C(const obj: IescObject; intFormat: integer = erfDetailedTechnical):
      string; override;
    function Translate(const strText: string): string; override;
    procedure DescribePayloadGraphic(const binding: IcabBinding; const payload, contextPayload: IInterface;
      const desc: IGraphicsDescription); override;
    procedure InternalRelease; override;
    procedure InternalUpdateFilterSet(const slFilterEntries: IStringInterfaceList); override;
  protected
    procedure DescribeLocator(const desc: IGraphicsDescription; const locEntry: IescLocator; const
      payloadPath: IPayloadPath); virtual;
  public
    constructor Create(const os: IescObjectSpace; const escSession: IescSession; const owner:
      IescControllerOwner); override;
  end;

implementation

uses
  b_AbstractControllerTypes,
  b_RuleResponses,
  b_Businessypes,
  b_Translation,
  dbu_Exceptions,
  dbu_StringUtils,
  SysUtils;

const
  cstrUnit = 'SampleUnit6.';

  { TBusinessClass }

function TBusinessClass.ExecuteRuleActionRequest(const ruleResponse: IescRuleResponse): boolean;
var
  i,
    intTemp: integer;
  ra: IescRuleActionRequest;
  aen: IpoAbstractEntry;
  cit: IpoCatalogItem;
  cli: IpoCatalogLink;
  escoSubject: IescObject;
  escoParameter: IescObject;
  param: IpoParameter;
begin
  result:= inherited ExecuteRuleActionRequest(ruleResponse);

  for i:= 0 to ruleResponse.GetActionRequestCount_R - 1 do begin
    escoSubject:= nil;
    ra:= ruleResponse.GetActionRequest_R(i);
    if ra.GetState_R = rarsPending then begin

      if ra.GetObject_R is TraCreateParameter then begin
        ra.SetState_R(rarsAborted);
        if (ra.GetObject_R as TraCreateParameter).locOwner.GetObject_EE(IescObject, escoSubject) then
          begin
          if Supports(escoSubject, IpoAbstractEntry, aen) then
            escoParameter:= aen.Parameters.AddNew(self, MN_EntryParameter)
          else if Supports(escoSubject, IpoCatalogLink, cli) then
            escoParameter:= cli.Parameters.AddNew(self)
          else if Supports(escoSubject, IpoCatalogItem, cit) then
            escoParameter:= cit.Parameters.AddNew(self)
          else begin
            escoParameter:= nil;
            assert(false, 'Oops, poSource has unexpected Type');
          end;
          if Supports(escoParameter, IpoParameter, param) then begin
            param.Definition.Add(self, (ra.GetObject_R as TraCreateParameter).locParameterDefinition);
            ra.SetState_R(rarsDone);
          end;
        end;
      end;

      if ra.GetObject_R is TraSetParameterValue then begin
        ra.SetState_R(rarsAborted);
        if (ra.GetObject_R as TraSetParameterValue).locParameter.GetObject_EE(IpoParameter, param) then
          begin
          param.SetValue(self, (ra.GetObject_R as TraSetParameterValue).strValue);
          ra.SetState_R(rarsDone);
        end
        else
          assert(false, 'Oops, poSource has unexpected Type');
      end;

      if ra.GetObject_R is TraSetEntryEnable then begin
        ra.SetState_R(rarsAborted);
        if (ra.GetObject_R as TraSetEntryEnable).locEntry.GetObject_EE(IpoAbstractEntry, aen) then begin
          aen.SetEnable(self, (ra.GetObject_R as TraSetEntryEnable).blnEnable);
          ra.SetState_R(rarsDone);
        end
        else
          assert(false, 'Oops, poSource has unexpected Type');
      end;

      if ra.GetObject_R is TraSetEntryQuantity then begin
        ra.SetState_R(rarsAborted);
        if (ra.GetObject_R as TraSetEntryQuantity).locEntry.GetObject_EE(IpoAbstractEntry, aen) then
          begin
          aen.SetQuantity(self, (ra.GetObject_R as TraSetEntryQuantity).realQuantity);
          aen.SetEnable(self, true);
          ra.SetState_R(rarsDone);
        end
        else
          assert(false, 'Oops, poSource has unexpected Type');
      end;

      if ra.GetObject_R is TraSetAttributeValue then begin
        ra.SetState_R(rarsAborted);
        if (ra.GetObject_R as TraSetAttributeValue).locOwningObject.GetObject_EE(IpoPhoenixObject,
          escoSubject) then begin
          if escoSubject.SetAttribute(self, (ra.GetObject_R as TraSetAttributeValue).strAttributName,
            (ra.GetObject_R as TraSetAttributeValue).varValue) then
            ra.SetState_R(rarsDone);
        end
        else
          assert(false, 'Oops, poSource has unexpected Type');
      end;

      intTemp:= -1;
      if ra.GetObject_R is TraSetSessionSequenceNbr then begin
        ra.SetState_R(rarsAborted);
        if (ra.GetObject_R as TraSetSessionSequenceNbr).locOwningSession.GetObject_EE(IpoSession,
          escoSubject) then begin
          if not (escoSubject as IpoSession).CheckSequence(self, intTemp) then begin
            assert(intTemp > 0, 'TBusinessClass.ExecuteRuleActionRequest: SequenceNbr must be >0');
            (escoSubject as IpoSession).SetSequence(self, intTemp);
            ra.SetState_R(rarsDone);
          end;
        end
        else
          assert(false, 'Oops, poSource has unexpected Type');
      end;

      if ra.GetState_R = rarsDone then begin
        result:= true;
        if assigned(escoSubject)
          and not (ra.GetObject_R is TraCreateParameter)
          then
          GetLocatorManager_C.AddReference((escoSubject as IescObjectFriend).GetLocator);
      end;

    end;
  end;

end;

procedure TBusinessClass.InternalUpdateFilterSet(const slFilterEntries: IStringInterfaceList);
var
  rootClass: IescRMGeneralClass;
begin
  rootClass:= GetObjectSpace_C.GetObjectModel_OS.GetRootClass_ME;
  if not Assigned(rootClass) then
    raise
      ExceptionPrepare(cstrUnit + ClassName + '.InternalUpdateFilterSet',
      'Model definition failed to provide a root object definition', EescModelIntegrity);
end;

function TBusinessClass.KeyToHintParagraph(
  const strKey: string): string;
begin
  result:= Translate(strKey);
  if result <> '' then
    result:= '<p>' + EscapeXMLBody(result) + '</p>';
end;

function TBusinessClass.GetBoundInterface(const IID: TGUID; out AInterface): boolean;
var
  poLink: IpoCatalogLink;
  locObj: IescLocator;
begin
  result:= false;
  Pointer(AInterface):= nil;
  locObj:= GetFocus_C;
  if Assigned(locObj) then begin
    result:= locObj.GetObject_EE(IID, AInterface);
    if not result and locObj.GetObject_EE(IpoCatalogLink, poLink) then begin
      locObj:= poLink.GetDetail; 
      result:= locObj.GetObject_EE(IID, AInterface);
    end;
  end;
end;

function TBusinessClass.GetBoundObject(out obj: IescObject): boolean;
var
  poLink: IpoLink;
  locDetail: IescLocator;
begin
  if Assigned(FlocFocus) then begin
    if FlocFocus.GetObject_EE(IpoLink, poLink) then begin
      locDetail:= poLink.GetDetail;
      result:= Assigned(locDetail) and locDetail.GetObject_EE(IescObject, obj);
    end
    else
      result:= FlocFocus.GetObject_EE(IescObject, obj);
  end
  else
    result:= false;
end;

function TBusinessClass.GetColumnWidthSettingIdentifier(const binding: IcabBinding): string;
begin
  Assert(Assigned(binding) and (binding.GetIdentifier_CAB<>''));
  result:= cstrSI_ColumnWidth+binding.GetIdentifier_CAB;
end;

function TBusinessClass.GetCustomText(const binding: IcabBinding;
  const payload, contextPayload: IInterface; const strFacet: string): string;
var
  loc: IescLocator;
  obj: IescObject;
begin
  if Supports(payload, IescLocator, loc) then begin
    result:= GetObjectRepresentation_C(loc.GetObject_EE, erfLong);
  end
  else if Supports(payload, IescObject, obj) then
    result:= GetObjectRepresentation_C(obj, erfLong)
  else
    result:= inherited GetCustomText(binding, payload, contextPayload, strFacet);
end;

procedure TBusinessClass.DoAdjustColumnWidth(const binding: IcabBinding; var realWidth: double);
var
  strIdentifier: string;
begin
  strIdentifier:= GetColumnWidthSettingIdentifier(binding);
  GetSession.GetPersonalSettings_S.SetString(FstrNamespace, strIdentifier, FloatToStr(realWidth));
end;

function TBusinessClass.DoGetCode(const binding: IcabBinding;
  const payload: IInterface): string;
var
  loc: IescLocator;
  obj: IescObject;
begin
  if Supports(payload, IescLocator, loc) then
    result:= loc.GetObject_EE.GetRepresentation(erfShort)
  else if Supports(payload, IescObject, obj) then
    result:= obj.GetRepresentation(erfShort)
  else
    result:= inherited DoGetCode(binding, payload);
end;

function TBusinessClass.DoGetInitialColumnWidth(const binding: IcabBinding): double;
var
  strIdentifier: string;
  strWidth: string;
const
  realDefaultWidth: double = 4;
begin
  strIdentifier:= GetColumnWidthSettingIdentifier(binding);
  strWidth:= GetSession.GetPersonalSettings_S.GetString(FstrNamespace, strIdentifier, FloatToStr(realDefaultWidth));
  result:= StrToFloatDef(strWidth, realDefaultWidth);
end;

procedure TBusinessClass.DescribePayloadGraphic(
  const binding: IcabBinding;
  const payload, contextPayload: IInterface;
  const desc: IGraphicsDescription);
var
  locObject: IescLocator;
  obj: IescObject;
begin
  if FhpApply.Matches(binding) then
    desc.SetResourceName(cstrRN_IconOK)
  else if FhpDiscard.Matches(binding) then
    desc.SetResourceName(cstrRN_IconCancel)
  else if FhpRefresh.Matches(binding) then
    desc.SetResourceName(cstrRN_IconRefresh)
  else begin
    if Supports(payload, IescLocator, locObject) then
      DescribeLocator(desc, locObject, nil)
    else if Supports(payload, IescObject, obj) then
      DescribeLocator(desc, (obj as IescObjectFriend).GetLocator, nil)
    else
      inherited;
  end;
end;

function TBusinessClass.ConvertTextToHint(const strText: string): string;
begin
  result:= strText;
  if result <> '' then
    result:= cstrStandardHintHeader + '<body>' + EncapsulateParagraphs(result) + '</body>' +
      cstrStandardHintFooter;
end;

constructor TBusinessClass.Create(const os: IescObjectSpace; const escSession: IescSession; const owner:
  IescControllerOwner);
begin
  SetLength(FObservers, 2);
  FintLastObserverIndex:= -1;
  SetLength(FObservables, 4);
  FintLastObservableIndex:= -1;
  inherited Create(os, escSession, owner);
end;

function TBusinessClass.Translate(const strText: string): string;
begin
  result:= T(strText);
end;

function TBusinessClass.DescribeCatalogLocator(
  const desc: IGraphicsDescription;
  const locEntry: IescLocator;
  const payloadPath: IPayloadPath): string;
var
  poDetail: IescObject;
  strTypeName: string;
  intIconIndex: integer;
  locParent: IescLocator;
  poCatalog: IpoCatalog;
  poCST: IpoCatalogStructure;
  poSIT: IpoServiceItem;
  poCIT: IpoCatalogItem;
  poVIT: IpoVersionableItem;
  intState: integer;
begin
  poDetail:= TraverseCatalogLinkAndResolve(self, locEntry);
  if assigned(poDetail) then begin
    if Supports(poDetail, IpoCatalogStructure, poCST) then begin
      poCatalog:= poCST.Catalog.GetAssociate(self);
      if poCatalog.SupportsItem(MN_ServiceItem) then
        strTypeName:= cstrRN_IconItemServiceCatalogStructure
      else if poCatalog.SupportsItem(MN_AllocationPackage) then
        strTypeName:= cstrRN_IconItemAllocationCatalogStructure
      else if poCatalog.SupportsItem(MN_PointOfService) then
        strTypeName:= cstrRN_IconItemHospitalStructureCatalogStructure
      else
        strTypeName:= cstrRN_IconItemServiceCatalogStructure;
    end
    else if Supports(poDetail, IpoServiceItem, poSIT) then begin
      poCatalog:= poSIT.Catalog.GetAssociate(self);
      if assigned(poCatalog) then begin
        intIconIndex:= poCatalog.GetItemIcon(self);
        case intIconIndex of
          0: strTypeName:= cstrRN_ItemType1;
          1: strTypeName:= cstrRN_ItemType2;
          2: strTypeName:= cstrRN_ItemType3;
          3: strTypeName:= cstrRN_ItemType4;
          4: strTypeName:= cstrRN_ItemType5;
          5: strTypeName:= cstrRN_ItemType6;
        end;
      end
      else begin
        strTypeName:= '';
      end;
    end
    else if locEntry.HasObjectOfType_EE(IpoCatalogSegmentItemLink)
      and Assigned(payloadPath)
      and (1 < payloadPath.GetPayloadCount)
      and payloadPath.GetPayloadByIndexAs(1, IescLocator, locParent)
      and locParent.HasObjectOfType_EE(IpoCatalogSegmentItemLink) then begin
      strTypeName:= 'PX Icon' + (locEntry.GetObjectRef_EE as
        IpoCatalogSegmentItemLink).MasterSegment.GetAssociate(self).GetClass.GetName_ME;
    end
    else if Supports(poDetail, IpoCatalog, poCatalog) then begin
      if Assigned(poCatalog.Factory.GetAssociate(self)) then
        strTypeName:= 'PX Icon ' + poCatalog.Factory.GetAssociate(self).GetCode
      else
        strTypeName:= 'PX Icon UnknownCatalogType';
    end
    else if Supports(poDetail, IpoCatalogItem, poCIT) then begin
      if Supports(poDetail, IpoStructuralItem) then begin
        strTypeName:= cstrRN_IconItemCatalogSegment;
      end
      else if Supports(poDetail, IpoCompositeItem) then begin
        strTypeName:= cstrRN_CodeList;
      end
      else if Supports(poDetail, IpoTaskItem) then begin
        strTypeName:= cstrRN_ItemTask;
      end
      else begin
        strTypeName:= poCIT.GetExtensions.GetStrValueByKey_EE(self, cstrCIExt_CatalogHierarchy);
        if strTypeName = '' then
          strTypeName:= 'PX Icon Item' + poDetail.GetClass.GetName_ME
        else
          strTypeName:= 'PX Icon Item' + strTypeName;
      end;
    end
    else if Supports(poDetail, IpoCatalogSegment) then begin
      strTypeName:= cstrRN_IconItemCatalogSegment;
    end
    else begin
      strTypeName:= 'PX Icon ' + poDetail.GetClass.GetName_ME;
    end;
    desc.SetResourceName(strTypeName);
    { Zus�tzliche Flags, dir ausschliesslich vom Objektzustand abh�ngig sind }
    if Supports(poDetail, IpoVersionableItem, poVIT) then begin
      intState:= poVIT.GetState;
      desc.SetFlag(cstrSTATE_ReadyToPublish, intState = Integer(cisReadyToPublish));
      desc.SetFlag(cstrSTATE_Published, intState = Integer(cisPublished));
      desc.SetFlag(cstrSTATE_Retired, intState = Integer(cisRetired));
      desc.SetFlag(cstrSTATE_ReadyToRetire, intState = Integer(cisReadyToRetire));
    end;
  end;
end;

procedure TBusinessClass.DescribeLocator(
  const desc: IGraphicsDescription;
  const locEntry: IescLocator;
  const payloadPath: IPayloadPath);
begin
  DescribeCatalogLocator(desc, locEntry, payloadPath);
end;

function TBusinessClass.GetLabelPath(const eelPath: IescExpressionElementList): string;
var
  i: integer;
  locEntry: IescLocator;
  strEntry: string;
  poObject: IpoPhoenixObject;
begin
  result:= '';
  for i:= 0 to eelPath.GetLastIndex_EEL do begin
    locEntry:= eelPath.GetLocatorByIndex_EEL(i);
    if not locEntry.HasObjectOfType_EE(IpoCatalogLink)
      and locEntry.GetObject_EE(IpoPhoenixObject, poObject) then begin
      strEntry:= poObject.GetRepresentation(erfLong);
      result:= Combine(result, strEntry, '\');
    end;
  end;
end;

function TBusinessClass.GetLeaf(
  const payload: IInterface;
  out leaf: IInterface): boolean;
var
  path: IPayloadPath;
begin
  if Supports(payload, IPayloadPath, path) then
    path.GetLeafAs(IInterface, leaf)
  else
    leaf:= payload;
  result:= Assigned(leaf);
end;

procedure TBusinessClass.InternalRelease;
var
  i: integer;
begin
  inherited;
  for i:= FintLastObservableIndex downto 0 do
    FObservables[i].RemoveObserver(self);
  for i:= FintLastObserverIndex downto 0 do
    FObservers[i].RemoveObservable(self);
end;

function TBusinessClass.GetSession: IBusinessSession;
begin
  result:= FSession as IBusinessSession;
end;

function TBusinessClass.GetUser: IPhoenixUser;
var
  session: IBusinessSession;
begin
  if not Supports(FSession, IBusinessSession, session) then
    raise ExceptionPrepare(cstrUnit + ClassName + '.GetUser', 'Invalid session');
  result:= session.GetUser;
  if not Assigned(result) then
    raise ExceptionPrepare(cstrUnit + ClassName + '.GetUser', 'Failed to retrieve user object');
end;

function TBusinessClass.GetUserObject: IpoUser;
var
  session: IBusinessSession;
begin
  if not Supports(FSession, IBusinessSession, session) then
    raise ExceptionPrepare(cstrUnit + ClassName + '.GetUserObject', 'Invalid session');
  result:= session.GetUser_S;
  if not Assigned(result) then
    raise ExceptionPrepare(cstrUnit + ClassName + '.GetUserObject', 'Failed to retrieve user object');
end;

procedure TBusinessClass.DispatchMessage(
  const intMessageType: integer);
var
  i: integer;
begin
  for i:= 0 to FintLastObserverIndex do
    FObservers[i].HandleMessage(self, intMessageType, nil);
end;

procedure TBusinessClass.DispatchMessage(
  const msg: IMessage);
var
  i: integer;
begin
  for i:= 0 to FintLastObserverIndex do
    FObservers[i].HandleMessage(self, msg.GetType, msg);
end;

function TBusinessClass.GetObject(
  const locObj: IescLocator;
  const IID: TGUID;
  out AInterface): boolean;
var
  poLink: IpoCatalogLink;
  locLinkDetail: IescLocator;
begin
  result:= false;
  Pointer(AInterface):= nil;

  if Assigned(locObj) then begin
    result:= locObj.GetObject_EE(IID, AInterface);
    if not result and locObj.GetObject_EE(IpoCatalogLink, poLink) then begin
      locLinkDetail:= poLink.GetDetail; 
      result:= locLinkDetail.GetObject_EE(IID, AInterface);
    end;
  end;
end;

function TBusinessClass.GetObjectRepresentation_C(const obj: IescObject;
  intFormat: integer = erfDetailedTechnical): string;
var
  poParam: IpoParameter;
  poParamDef: IpoParameterDefinition;
  poMeasure: IpoMeasure;
  poEntry: IpoAbstractEntry;
  poItem: IpoCatalogItem;
  poPerson: IpoPerson;
begin
  if (obj = nil) then begin
    result:= '<' + Translate('not assigned') + '>';
    exit;
  end;
  case intFormat of
    erfShort, erfShortTechnical: result:= obj.GetRepresentation(intFormat);
    erfLong, erfDetailed: begin
        if Supports(obj, IpoParameter, poParam) then begin
          if Supports(poParam.Definition.GetAssociate(self), IpoParameterDefinition, poParamDef) then begin
            result:= GetObjectRepresentation_C(poParamDef, intFormat);
            Concatenate(result, '=', poParamDef.FormatValueForRepresentation(self, poParam.GetValue,
              intFormat));
          end
          else
            result:= poParam.GetRepresentation(erfShort);
        end
        else if Supports(obj, IpoParameterDefinition, poParamDef) then begin
          if not poParamDef.GetLabelIsNull then
            result:= poParamDef.GetLabel.GetStrValue_EE(self);
          if Supports(poParamDef.Measure.GetAssociate(self), IpoMeasure, poMeasure) then
            result:= Format(cstrMeasureLabelFormat, [result, poMeasure.GetRepresentation(erfShort)]);
        end
        else if Supports(obj, IpoPerson, poPerson) then begin
          result:= Combine(poPerson.GetName, poPerson.GetFirstName, ' ');
        end
        else
          result:= obj.GetRepresentation(intFormat);
      end;
    erfDetailedTechnical: begin
        if Supports(obj, IpoAbstractEntry, poEntry) then begin
          result:= poEntry.GetRepresentation(erfShort);
          if Supports(poEntry.ItemParticipant.GetAssociate(self), IpoCatalogItem, poItem) then
            result:= result + '[' + poItem.GetCode + ']';
        end
        else
          result:= obj.GetRepresentation(intFormat);
      end;
  else
    result:= obj.GetRepresentation(intFormat);
  end;
end;

function TBusinessClass.MayAdmin(const securedObject: IpoSecuredObject): boolean;
var
  user: IPhoenixUser;
begin
  user:= GetUser;
  result:= user.IsSuperUser(self)
    or user.IsSystemGroupUser(self)
    or (Assigned(securedObject) and securedObject.GetAdminBit(self, user));
end;

function TBusinessClass.MayRead(const securedObject: IpoSecuredObject): boolean;
begin
  result:= MayWrite(securedObject) 
  or (Assigned(securedObject) and securedObject.GetReadBit(self, GetUser));
end;

function TBusinessClass.MayExecute(const securedObject: IpoSecuredObject): boolean;
begin
  result:= Assigned(securedObject)
    and securedObject.GetExecuteBit(self, GetUser);
end;

function TBusinessClass.MayWrite(const securedObject: IpoSecuredObject): boolean;
begin
  result:= MayAdmin(securedObject)
    or (Assigned(securedObject) and securedObject.GetWriteBit(self, GetUser));
end;

function TBusinessClass.ResolveActByUID(
  const intActUID: integer): IpoAct;
var
  eelActs: IescExpressionElementList;
  strExp: string;
  locAct: IescLocator;
  ne: Exception;
begin
  try
    strExp:= Format(
      '//*/Act[@escid=%d]',
      [intActUID]
      );
    eelActs:= EvaluateExpression_C(strExp, emFull);
    eelActs.EnsureAll(self, true);
    if eelActs.GetLastIndex_EEL >= 0 then begin
      locAct:= eelActs.GetLocatorByIndex_EEL(0);
      locAct.GetObject_EE(IpoAct, result);
    end;
  except
    on e: Exception do begin
      ne:= ExceptionChain(
        e, cstrUnit + ClassName + '.ResolveActByUID',
        'Failed to resolve act'
        );
      ExceptionMessageAddAttribute(ne, 'Act UID', intActUID);
      raise ne;
    end;
  end;
end;

function TBusinessClass.ResolveParameterDefinition(
  const controller: IAbstractController;
  const strCode: string;
  out paramDef: IParameterDefinition): boolean;
var
  strExp: string;
  eelDefs: IescExpressionElementList;
begin
  strExp:= Format('//*/%s[%s=%s]', [MN_ParameterDefinition, '@code', Quote(strCode)]);
  eelDefs:= controller.SelectList_C(strExp, semFull);
  if eelDefs.GetCount_EEL = 1 then begin
    eelDefs.GetLocatorByIndex_EEL(0).GetObject_EE(IParameterDefinition, paramDef);
    result:= Assigned(paramDef);
  end
  else
    result:= false;
end;

function TBusinessClass.ResolvePatientByCode(
  const intPatientCode: integer): IpoPatient;
var
  strExpression: string;
  locPatient: IescLocator;
  ne: Exception;
begin
  { TODO -ofvk -crefactoring : ResolvePatientByCode in PhoenixAdapter schieben... }
  try
    strExpression:= Format('//*/%s[@Code=''%d'']', [MN_Patient, intPatientCode]);
    locPatient:= SelectLocator_C(strExpression, semSmart);
    if Assigned(locPatient) then
      locPatient.GetObject_EE(IpoPatient, Result)
    else
      Result:= nil;
  except
    on e: Exception do begin
      ne:= ExceptionChain(e,
        cstrUnit + ClassName + '.ResolvePatientByCode',
        'Failed to resolve patient'
        );
      ExceptionMessageAddAttribute(ne, 'Patient code', intPatientCode);
      raise ne;
    end;
  end;
end;

function TBusinessClass.MayAdmin(const element: ICatalogElement): boolean;
var
  user: IPhoenixUser;
begin
  user:= GetUser;
  result:= user.IsSuperUser(self)
    or user.IsSystemGroupUser(self)
    or (Assigned(element) and element.GetAdminBit(self, user));
end;

function TBusinessClass.MayExecute(const element: ICatalogElement): boolean;
begin
  result:= Assigned(element)
    and element.GetExecuteBit(self, GetUser);
end;

function TBusinessClass.MayRead(const element: ICatalogElement): boolean;
begin
  result:= MayWrite(element) 
  or (Assigned(element) and element.GetReadBit(self, GetUser));
end;

function TBusinessClass.MayWrite(const element: ICatalogElement): boolean;
begin
  result:= MayAdmin(element)
    or (Assigned(element) and element.GetWriteBit(self, GetUser));
end;

function TBusinessClass.MayAdmin(const acl: IAccessControlList): boolean;
var
  user: IPhoenixUser;
begin
  user:= GetUser;
  result:= user.IsSuperUser(self)
    or user.IsSystemGroupUser(self)
    or (Assigned(acl) and acl.MayAdmin(self, user.GetGroup(self)));
end;

function TBusinessClass.MayExecute(const acl: IAccessControlList): boolean;
begin
  result:= Assigned(acl)
    and acl.MayExecute(self, GetUser.GetGroup(self));
end;

function TBusinessClass.MayRead(const acl: IAccessControlList): boolean;
begin
  result:= MayWrite(acl) 
  or (Assigned(acl) and acl.MayRead(self, GetUser.GetGroup(self)));
end;

function TBusinessClass.MayWrite(const acl: IAccessControlList): boolean;
begin
  result:= MayAdmin(acl)
    or (Assigned(acl) and acl.MayWrite(self, GetUser.GetGroup(self)));
end;
end.
