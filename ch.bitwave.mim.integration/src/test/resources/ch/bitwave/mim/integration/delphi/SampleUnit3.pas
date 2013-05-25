{*
 *}
unit SampleUnit3;

interface

uses
  Controls, // Requires TCursor in ICursorManager
  Classes, // Required for IStringInterfaceList
  SysUtils;  //Required for Exception

const

  cstrGUID_IInterfaceList = '{285DEA8A-B865-11D1-AAA7-00C04FB17A72}';
  cstrGUID_ICursorManager = '{6B7E2FA0-4842-436C-ADB2-54A78A10CFB2}';
  cstrGUID_IOwnedCollectionItem = '{FE0A839E-29C0-4D11-983B-8A681D6A1725}';
  cintIID_IOwnedCollectionItem: TGUID = cstrGUID_IOwnedCollectionItem;
  cstrGUID_IOwnerCollection = '{59F8E4E9-15CF-4407-9FCC-C5E200CC74BE}';
  cstrGUID_IStringList = '{0676E164-0AB6-4CF2-BC30-0ADD1BAF7347}';
  cstrGUID_IStringListReplaceSystemText = '{91B91FC6-7F9B-4ECD-AA4D-1F5FD497BC9B}';
  cstrGUID_IList = '{D801A6E7-4C7A-42B9-82DE-DF5362C9D900}';
  cstrGUID_INameValueList = '{8A0F3297-A36A-4E0E-A5BF-44CF2B64D8E2}';
  cstrGUID_IStringInterfaceList = '{24017702-50FF-4763-9AAF-96C933D4E5C5}';
  cstrGUID_IStringIntegerList = '{452037B9-E65D-4143-8F42-A163834881C1}';
  cstrGUID_IStringStringList = '{13EAD7D4-87DF-40FF-AC6D-8FBA406B1E6D}';
  cstrGUID_IStringRealList = '{0FBABCE1-F7F7-4125-A005-1C992FBE7134}';
  cstrGUID_IKeypointList = '{3E008A61-2732-45FE-A10E-46D383EAB6B3}';
  cstrGUID_IIntegerList = '{14979665-52E9-4088-BAC6-E85C58AA92A2}';
  cstrGUID_IRealList = '{98A56CDE-309A-444B-9DEE-C801AD14060F}';
  cstrGUID_IIntegerInterfaceList = '{2D9B2E0A-87B5-4F9F-968C-70D9942D059E}';
  cstrGUID_IIntegerIntegerList = '{326AF1DA-514B-42DB-B2C8-D0AAC4103B13}';
  cstrGUID_IIntegerRealList = '{31EBD2F2-4302-4AFD-A981-D1B4B51D877D}';
  cstrGUID_IIntegerStringList = '{0EDD3D87-51DB-412E-894A-20773646ED80}';
  cstrGUID_IRealInterfaceList = '{94217EE4-BE59-4EF9-908F-2EEB7256C663}';
  cstrGUID_IExternalProcess = '{9312168E-D5D1-40EB-A0B6-B08FDE0A3BC5}';
  cstrGUID_IDateTime = '{4A8EF101-3A33-4DD2-AA5D-744862561E34}';
  cstrGUID_IVariant = '{7C6CB1FB-EA2C-492C-A0D7-2757AA1DA995}';
  cstrGUID_IMessage = '{D46A1957-07C8-45A5-BCCA-9E39393C61EE}';
  cstrGUID_IMAttributeChange = '{E7A2EB88-C657-447E-AE32-A1AC206B5C3F}';
  cstrGUID_IMModuleInvocation = '{3E9DDF14-B1CF-40A4-BE2B-FEF8A1EA5BDC}';
  cstrGUID_IMCode = '{C32A1377-9461-41DA-8359-464CF0D038C8}';
  cstrGUID_IMCodedPayload = '{6D9D34EE-9431-4090-AC1D-20CA512EBCB9}';
  cstrGUID_IMPromptYesNo = '{420C3896-AE6E-421E-9B46-651E53C78874}';
  cstrGUID_IMNumericalAttributeParameters = '{DD147184-0E4D-487D-BB5E-41D134AF1E33}';
  cstrGUID_IModuleInteraction = '{0263D344-E946-4EC2-A4FD-0770B55F1755}';
  cstrGUID_IMessageFactory = '{A8F5506B-7498-4C40-AD79-35969C9F99FC}';
  cstrGUID_IObservable = '{BCF2FF48-8A9E-4FEF-A466-C3A1A62EE193}';
  cstrGUID_IObserver = '{89C94338-6E0E-470A-B88D-AD67DD28975C}';
  cstrGUID_ILinAcc = '{C9ABC245-C519-4222-AD0C-55BFAC288F3A}';
  cstrGUID_ITraceHost = '{15ECC3E8-0410-4B4A-89D6-76DB105E77AA}';
  cstrGUID_ISegementListener = '{E92568FF-CFE1-4E3E-9178-124E35A46AD3}';
  cstrGUID_IDataElement = '{9F70A98D-2E96-4146-8580-1C2D42BC881F}';
  cstrGUID_IQualitativeDataElement = '{864B0BAF-7F60-4863-B88B-75DC875F5C8A}';
  cstrGUID_ISimpleDataElement = '{6C1AA615-FAB7-4BC7-9520-BCB59BC45E42}';
  cstrGUID_IRangeDataElement = '{E34C672D-4225-405D-B720-6BBBD2778949}';
  cstrGUID_IGanttDataElement = '{92850E4D-5FC9-4E5D-A70F-5D63E71B4069}';
  cstrGUID_ITextDataElement = '{741EB3D5-9199-4BF2-B285-FC2CEF8BC2E9}';
  cstrGUID_IKeypoint = '{C6D87B3F-8965-4121-AE11-02FCDD30E982}';
  cstrGUID_ISimpleKeypoint = '{3D5D7CF3-F332-45A8-A137-A2FA3CA0B6AB}';
  cstrGUID_ISAXReader = '{FD3AAB96-B98B-4195-B63D-57AA1A44A272}';
  cstrGUID_ISAXWriter = '{F0AD0D8F-AFAA-4973-9CF8-4B75B71B558B}';
  cstrGUID_ISAXElementHandler = '{97807973-CAF5-4C0B-A001-C897C5FDBBA6}';
  cstrGUID_ISAXCommentHandler = '{F0B3A7C1-A545-4226-BB20-968BE4A13320}';
  cstrGUID_ISAXProcessingInstructionHandler = '{836A2275-CB51-4F05-B2B9-1E127B369D8A}';
  cstrGUID_IImageConverter = '{C28B6301-6873-4EE2-9424-1E079A79591A}';
  cstrGUID_IGraphicsProviderHost = '{22450436-0CCF-45EF-8410-3D17EE51F737}';
  cstrGUID_IGraphicsProvider = '{EEE43FA6-24C4-4EAD-B1E4-ACBDD30D14E9}';
  cstrGUID_IClassicImageProvider = '{87BA0A64-ECFD-49C9-9132-F886F5FA1A2B}';
  cstrGUID_IDocumentConverter = '{B79AD936-2D61-46E6-94ED-C7CC0CBA25EE}';
  cstrGUID_ITimer = '{28603171-6373-4224-A901-87802FBF2BB3}';
  cstrGUID_ITimerHost = '{C0431FB7-DF9A-47AC-9D1D-1C37D666CDC9}';
  cstrGUID_ILogHost = '{4954C93A-6619-4A79-9AB5-17C6919047B1}';
  cstrGUID_ILogClient = '{14F2CFFF-9834-48A2-9912-E3C9613E00D3}';
  cstrGUID_IEnumeration = '{1D2D4723-6B81-4492-9D95-2A8462FAA305}';
  cstrGUID_IEnumerationValue = '{799897C5-2613-4D96-AB4F-B54BD01BE78B}';
  cstrGUID_ISATableData = '{9686D991-D3FC-4188-8F40-5CBEE094B0B7}';
  cstrGUID_IRATableData = '{BAE8DF55-76BA-4CA2-B601-A617C31E5E2D}';
  cstrGUID_ITableWriter = '{5E8C6625-3C0B-4FA4-8A29-0BAFC3B18894}';
  cstrGUID_ITableReader = '{7501E055-B42F-4D2A-8BB7-EA7369669D8B}';
  cstrGUID_ITextRepresentation = '{0B1A1FDA-6491-41F0-ACE3-8268D307846B}';
  cstrGUID_IStreamlet = '{37AFB070-0709-4108-96F9-0747EBD74729}';
  cstrGUID_IStreamReader = '{AE1AFF37-6F0F-45DC-9334-71081780CBBA}';
  cstrGUID_IStreamWriter = '{CA193F91-1CE7-4523-9253-69095BA037B8}';
  cstrGUID_IMemoryStreamlet = '{48B2EB30-F360-432A-85AA-5DA6CBAEED68}';
  cstrGUID_ITempFile = '{BACB539D-D087-44F9-AED8-0E9B230CCFA9}';
  cstrGUID_ISilentPrinter = '{3ED598F0-4252-4D24-97B7-FB477878D197}';
  cstrGUID_IStyle = '{792B2B43-CACE-44A1-ADA3-1938C1106EA7}';
  cstrGUID_IDocumentReader = '{72305313-5D97-4C0A-860A-50D4A565298E}';
  cstrGUID_IDocumentWriter = '{05FF9BD3-A650-40E0-9E80-81ED08DF38D7}';
  cstrGUID_ISpellChecker = '{BCDE26E8-3978-4881-BF94-8DEBDDA4CCCD}';
  cstrGUID_ICryptEngine = '{8A824636-04D6-4079-8A4A-6C497F2CD902}';
  cstrGUID_IStopWatch = '{407B03E1-0A3B-4A24-A3FE-B73EA5BF0852}';
  cstrGUID_IType = '{55222187-8E52-42FE-BFEB-648BC260218F}';
  cstrGUID_IFunctionType = '{47B1565F-BD7C-41CC-88EA-C9F23A16AE33}';
  cstrGUID_ITypeInstance = '{F59D842D-4DFF-4006-925F-C777BAF179D1}';
  cstrGUID_ISimpleType = '{00964CBE-5D6F-4594-9C23-A70F9CCE26D9}';
  cstrGUID_ICompositeType = '{B18BCC12-5295-478E-A5F1-6C7248EA3019}';
  cstrGUID_IInvocableType = '{792476DA-1214-4090-8E32-5BE7A3D2F6AE}';
  cstrGUID_ITypeCollection = '{71DCE961-93D1-41BA-A418-66FD6175F238}';
  cstrGUID_ITypedStorage = '{16797707-F1A8-49AA-BEB6-C5A77611689E}';
  cstrGUID_IStringHashTable = '{6685CAED-9121-4BF3-8B6E-B92BC18221DF}';
  cstrGUID_IIntegerHashTable = '{D81F9EAA-27CA-4189-875A-034B5645D4F1}';
  cstrGUID_IInterfaceHashTable = '{9236A7AE-91DB-452E-9B76-02D037741144}';
  cstrGUID_IInterfaceHashTableIterator = '{E20E07E6-E733-4D80-84E0-18105E8C034F}';
  cstrGUID_IDOMStreamer = '{EF650DE4-B436-4865-8895-5A243DC12139}';
  cstrGUID_ISourceParserHost = '{FA8AF209-DA71-4E39-84BE-A4DC5D96F9AD}';
  cstrGUID_ISourceParser = '{6531084E-AA3F-414B-B4A6-DFAB5E18FA13}';
  cstrGUID_IInstanceMonitor = '{FFE49E94-A280-4FAF-A9F1-5775DF9518B9}';
  cstrGUID_IStreamingFactory = '{70C10DD3-6D24-4A3E-BDB7-BC9ADF8AA159}';
  cstrGUID_IListFactory = '{85E3E37D-187E-46DD-918B-E6A6334DC669}';
  cstrGUID_IGraphicsDescription = '{0CEAE66F-10AD-4105-98E2-72D7AF795453}';
  cstrGUID_IIni = '{FEF9D56B-541E-43C0-864E-FAD3A2CD520C}';
  cstrGUID_IIniFactory = '{A68C152A-E164-435F-8AC4-A4828E310EC4}';
  cstrGUID_IAxisDefinition = '{C713D7E6-171F-4437-AF0C-DC8B9DAF7D6D}';
  cstrGUID_ISeriesDefinition = '{D03A67D6-24D1-458E-83E5-BB412A11A10A}';
  cstrGUID_ISeriesDefinitionFriend = '{0A6C3DF3-D860-4819-A414-2D8A43D866E1}';
  cstrGUID_IStorableDefinition = '{9D3AA098-62CB-48DF-A8B6-E431E6F183AF}';
  cstrGUID_IStorableDefinitionFriend = '{D6E15B2E-2CF6-49E4-B23D-6451AFDAB57F}';
  cstrGUID_IStorableDefinitionAdapter = '{1E2E8BFB-BB2E-4D3D-B394-BFD70E9D68B6}';
  cstrGUID_IStorableDefinitionFactory = '{21CB7E20-F4F7-4252-A025-E0E0DEDDC263}';
  cstrGUID_IAdapterFactory = '{59150D7E-5A65-4921-9DC8-3C041C7A1D18}';
  cstrGUID_IClassInfo = '{98D9DF54-FCC6-4251-A2CB-EEE610F65479}';
  cstrGUID_IStructReader = '{328803EF-848D-41FB-9D88-EB454537D25B}';
  cstrGUID_IStructWriter = '{D40C9707-99A5-42F5-B007-BB4999997ABC}';
  cstrGUID_IStructDocument = '{00BC9D17-F095-43A7-B969-D4F29121B30C}';
  cstrGUID_IStructDocumentPlain = '{22E83B81-4BF6-4486-AF84-9FFA1ADACCE6}';
  cstrGUID_IStructDocumentFriend = '{BDED462E-24FD-4377-8AD5-794158A09CE0}';
  cstrGUID_IClonable = '{32BC649B-7F2D-42E4-8800-1BFA733C0367}';
  cstrGUID_IPrototypeFactory = '{17E45A5D-DF62-480E-B1B6-36A1FA2A30D4}';
  cstrGUID_IUserInteraction = '{B6195159-04EF-406B-AB33-DA9B69258A1D}';
  cstrGUID_IUser = '{1DD1060A-3708-45A9-BE26-6918A323407E}';
  cstrGUID_IUserFriend = '{52DA440C-81B2-42E6-8158-5842396F33DB}';
  cstrGUID_ISession = '{2C9F82B6-53C5-4F83-A7E3-D794D06B2872}';
  cstrGUID_ISessionFriend = '{F72DE3DC-C8A5-44D6-AD6B-9763890B8222}';
  cstrGUID_IApplication = '{35089A1B-D2D8-436B-BAF1-4DC308F6ED2B}';
  cstrGUID_IApplicationFriend = '{DC20F269-838A-4736-A5A2-87F134B48C6D}';
  cstrGUID_IWorkflowPhase = '{26B49FC4-2DB8-4DB8-8C7B-30225F51A2E1}';
  cstrGUID_IIndexedString = '{7654D92F-692D-4930-B637-D1B41BC1D72B}';
  cstrGUID_IGenericBusinessObject = '{79B27F40-7847-49EF-8E70-70F6D2C2B414}';
  cstrGUID_IFileSelection = '{ECC1E782-50FF-4B0D-A3A6-DDE9A9607648}';
  cstrGUID_IGenericOption = '{C7074FEA-0E7D-4B4F-850D-8044CD212ACF}';
  cstrGUID_ILibraryFunction = '{FFEE2EC9-0401-4165-B7CE-A8DA10E9EEED}';
  cstrGUID_ILibrary = '{CDC5EBED-B5FF-4411-A7D2-6BCA5C475FCA}';
  cstrGUID_IMemoText = '{DF32176D-1057-414A-B8F5-0D274B81F315}';
  cstrGUID_IPayloadPath = '{BA5236A4-2D17-4584-82FD-DDD8DD3560B1}';
  cstrGUID_IPayloadNode = '{0A212A5E-478E-481A-B67F-C8A00F31280F}';
  cstrGUID_IReport = '{57341DE7-8E05-404A-B812-35E70E7FFAED}';
  cstrGUID_IReportSummary = '{6ECFB331-604D-40A3-A754-FFB90D3AC564}';
  cstrGUID_IReportFactory = '{BD0B88C6-3712-48C4-B4F9-D600E1CC8E53}';
  cstrGUID_IError = '{C10E9B51-FF1B-4FFA-ACE0-154937B749BE}';
  cstrGUID_IInfo = '{EE31E65B-9CFA-4BF4-9E5C-514CFDD3033F}';
  cstrGUID_IWarning = '{165C0DFB-EDCD-444A-BFD8-7CF882F85F96}';
  cstrGUID_IProgress = '{D913F094-1FFF-4A14-B828-3E0A7764E44A}';
  cstrGUID_IcaRenderContext = '{231C8E9C-850A-4806-B003-EB5538AF440A}';
  cstrGUID_IcaRenderContextDevice = '{0EBB42B2-6904-417E-8E77-B3DB2B690603}';
  cstrGUID_IImagePalette = '{EA163973-D7A2-4A83-AFA7-512616D438F6}';
  cstrGUID_IImageLayer = '{A205D0DA-EC07-4ADE-901D-2F5A34813E3A}';
  cstrGUID_IImageLayerIndexed = '{3715BCBC-DEB4-410A-AFF4-F9E1F9A71A83}';
  cstrGUID_IImageLayerFriend = '{FBB12619-DDEE-4068-BC27-B25280351298}';
  cstrGUID_IImage = '{C4F1B8C6-DC69-496B-BE95-93ADDE50E983}';
  cstrGUID_IImageAdapter = '{218E6646-D541-4486-B39D-85A6BA396D49}';
  cstrGUID_IImageAdapterFactory = '{BF21C195-90F4-4C49-981C-7B9B6547B1FC}';
  cstrGUID_IImageAcquisition = '{2A0E5EDE-2813-4728-A3C4-46E87A3A6880}';
  cstrGUID_IImageAcquisitionContext = '{D72C4B92-F3F1-4936-8E5B-B81A5F3738C2}';
  cstrGUID_ITextQuery = '{F496F33A-03D5-4854-9670-225A892EF3E3}';
  cstrGUID_IPath = '{50FF809B-11F2-44CB-A3FA-B97147F9A486}';
  cstrGUID_IPrintEngine = '{465BDBE5-654C-431B-8438-1D867B817336}';
  cstrGUID_IPage = '{1CF5CEAB-4898-4190-BC9D-1B4273E106D1}';
  cstrGUID_IPageBuilder = '{82099893-458D-4611-9F42-B9DB1506F7C1}';
  cstrGUID_IPageDefinition = '{5F6E2F13-5D3A-4E6F-9FCB-4331B53409C2}';
  cstrGUID_ITimeProvider = '{0E5BD961-03D2-42DF-8665-D206252D0B69}';
  cstrGUID_IStringWrapper = '{4BA69445-66C1-4E11-92F4-1FD5206779B4}';
  cstrGUID_IIntegerWrapper = '{2A98E70A-18A5-42C9-93A6-1B8A1BB33721}';
  cstrGUID_IWrapperFactory = '{B531C3CC-D1E6-4D30-BD9B-4200B2D79D8B}';
  cstrGUID_ITraceFilter = '{46873D09-2551-4725-A425-CF0703DD7FEB}';

type

  TBasicCompareStrings = function(const strEntry1, strEntry2: string): integer of object;

  IOwnerCollection = interface;
  IObserver = interface;
  IObservable = interface;
  ISourceParser = interface;
  ISourceParserHost = interface;
  ILogClient = interface;
  IStreamReader = interface;
  IStreamWriter = interface;
  IStructDocument = interface;
  IUser = interface;
  ISession = interface;
  ISessionFriend = interface;
  IApplication = interface;
  IApplicationFriend = interface;
  IStructReader = interface;
  IStructWriter = interface;
  IGraphicsDescription = interface;
  IError = interface;
  IWarning = interface;
  IInfo = interface;

  IClassInfo = interface
    [cstrGUID_IClassInfo]
    function ClassName: ShortString;
    function ClassType: TClass;
  end;

  IStreamlet = interface
    [cstrGUID_IStreamlet]
    procedure Release;
    function GetInternalReader: IStreamReader;
    function GetInternalWriter: IStreamWriter;
  end;

  IStringWrapper = interface
    [cstrGUID_IStringWrapper]
    function getString: string;
    procedure setString(const strValue: string);
  end;

  IIntegerWrapper = interface
    [cstrGUID_IIntegerWrapper]
    function getInteger: integer;
    procedure setInteger(const intValue: integer);
  end;

  IPrimitiveWrapperFactory = interface
    [cstrGUID_IWrapperFactory]
    function createStringWrapper(const strValue: string): IStringWrapper; overload;
    function createIntegerWrapper(const intValue: integer): IIntegerWrapper; overload;
    function createStringWrapper(const strValue: string; IsNull: Boolean): IStringWrapper; overload;
    function createIntegerWrapper(const intValue: integer;  IsNull: Boolean): IIntegerWrapper; overload;
    function CreateQuotedStringOutOfStringWrapper(const Wrapper: IStringWrapper): string; overload;
    function CreateStringOutOfIntegerWrapper(const Wrapper: IIntegerWrapper): string; overload;
  end;

  IStreamHandler = interface
    function GetPosition: Int64;
    procedure SetPosition(const intPosition: Int64);
    procedure Reset;
    function Seek(const intOffset: Int64; const enumOrigin: TStreamSeekOrigin): Int64;
  end;

  IStreamReader = interface(IStreamHandler)
    [cstrGUID_IStreamReader]
    function EOF: boolean;
    function GetSize: Int64;
    function GetText: string;
    function PeekByte: byte;
    function Read(var Buffer; const intCount: integer): integer;
    function ReadByte: byte;
    function ReadDouble: double;
    function ReadDoubleWord: Cardinal;
    function ReadLine: string;
    function ReadNullTerminatedString: string;
    function ReadWord: Word;
    procedure ReadBuffer(var Buffer; const intCount: integer);
  end;

  IStreamWriter = interface(IStreamHandler)
    [cstrGUID_IStreamWriter]
    function CopyFrom(const Source: IStreamReader; const intCount: Int64): Int64;
    function Write(const Buffer; const intCount: integer): integer;
    procedure Clear;
    procedure SetSize(const intSize: Int64);
    procedure SetText(const strText: string);
    procedure WriteBuffer(const Buffer; const intCount: integer);
    procedure WriteByte(const intValue: byte);
    procedure WriteDouble(const realValue: double);
    procedure WriteDoubleWord(const intValue: cardinal);
    procedure WriteLine(const strLine: string);
    procedure WriteNullTerminatedString(const strValue: string);
    procedure WriteString(const strText: string);
    procedure WriteWord(const intValue: word);
  end;

  IMemoryStreamlet = interface(IStreamlet)
    [cstrGUID_IMemoryStreamlet]
    function GetBaseAddress: Pointer;
  end;

  IOwnedCollectionItem = interface
    [cstrGUID_IOwnedCollectionItem]
    function BeforeRegister(const ownerInterface: IInterface): boolean;
    procedure AfterRegister(const ownerInterface: IInterface; intRegisteredIndex: integer);
    function BeforeUnregister(const ownerInterface: IInterface): boolean;
    procedure AfterUnregister(const ownerInterface: IInterface);
  end;

  IOwnerCollection = interface
    [cstrGUID_IOwnerCollection]
    function GetItemCount: integer;
    function GetItemByIndex(intIndex: integer): IOwnedCollectionItem;
    procedure RegisterItem(const item: IOwnedCollectionItem);
    procedure UnregisterAll;
    procedure UnregisterItem(intIndex: integer);
  end;

  INameValueList = interface
    [cstrGUID_INameValueList]
    function FindValue(const strName: string; out strValue: string): boolean;
    function GetLastIndex: integer;
    function GetName(const intIndex: Integer): string;
    function GetValue(const strName: string): string;
    function GetValueByIndex(const intIndex: Integer): string;
    function IndexOfName(const Name: string): Integer;
    procedure SetValue(const Name, Value: string);
  end;

  IList = interface
    [cstrGUID_IList]
    function GetCommaText: string;
    function GetCount: Integer;
    function GetDelimitedText(const chrDelimiter: char): string;
    function GetDuplicates: TDuplicates;
    function GetLastIndex: integer;
    function GetSorted: boolean;
    function GetText: string;
    procedure AddList(const other: IList);
    procedure Assign(const value: IInterface);
    procedure Clear;
    procedure Delete(const intIndex: Integer);
    procedure Exchange(const intIndex1, intIndex2: Integer);
    procedure LoadFromFile(const strFileName: string);
    procedure LoadFromStream(const stream: IStreamReader);
    procedure Move(const intCurIndex, intNewIndex: Integer);
    procedure SaveToFile(const strFileName: string);
    procedure SaveToStream(const stream: IStreamWriter);
    procedure SetCapacity(const intCapacity: integer);
    procedure SetCommaText(const strText: string);
    procedure SetDelimitedText(const chrDelimiter: char; const strText: string);
    procedure SetDuplicates(const Value: TDuplicates);
    procedure SetSorted(const blnSorted: boolean);
    procedure SetText(const strText: string);
    procedure Sort;
  end;

  IStringList = interface(IList)
    [cstrGUID_IStringList]
    function Add(const strKey: string): Integer;
    function Find(const strKey: string; out intIndex: Integer): boolean;
    function Get(const intIndex: integer): string;
    function GetValue(const Name: string): string;
    function GetCaseSensitive: boolean;
    function IndexOf(const strKey: string): Integer;
    procedure addToLastLine(const strValue: string);
    procedure Insert(const intIndex: Integer; const S: string);
    procedure Put(const intIndex: Integer; const S: string);
    procedure SetCaseSensitive(const Value: boolean);
  end;

  IStringListReplaceSystemText = interface(IStringList)
    [cstrGUID_IStringListReplaceSystemText]
    procedure Readln(out line: string);
    procedure writeln(const line: string);
    procedure write(const text: string);
    function getisEoF: Boolean;
  end;

  IIntegerList = interface(IList)
    [cstrGUID_IIntegerList]
    function Add(const intKey: integer): Integer;
    function Find(const intKey: integer; out intIndex: Integer): Boolean;
    function Get(const intIndex: Integer): integer;
    function IndexOf(const intKey: integer): Integer;
    procedure Insert(const intIndex: Integer; const intNumber: integer);
    procedure Put(const intIndex: Integer; const intKey: integer);
  end;

  IRealList = interface(IList)
    [cstrGUID_IRealList]
    function Add(const realNumber: double): Integer;
    function Find(const realKey: double; out intIndex: integer): boolean;
    function Get(const intIndex: Integer): double;
    function IndexOf(const realNumber: double): Integer;
    procedure Put(const intIndex: Integer; const realKey: double);
  end;

  IStringInterfaceList = interface(IStringList)
    [cstrGUID_IStringInterfaceList]
    function AddInterface(const strKey: string; const ref: IInterface): Integer;
    function Extract(const Item: IInterface): IInterface; overload;
    function Extract: IInterfaceList; overload;
    function GetInterface(const intIndex: Integer): IInterface;
    function IndexOfInterface(const ref: IInterface): Integer;
    procedure InsertInterface(const intIndex: Integer; const strKey: string; const ref: IInterface);
    procedure PutInterface(const intIndex: Integer; const ref: IInterface);
  end;

  IStringIntegerList = interface(IStringList)
    [cstrGUID_IStringIntegerList]
    function AddInteger(const strKey: string; const intValue: integer): Integer;
    function Extract(const intValue: integer): integer;
    function GetInteger(const intIndex: Integer): integer;
    function IndexOfInteger(const intInteger: Integer): integer;
    procedure PutInteger(const intIndex: Integer; const intValue: integer);
  end;

  IStringStringList = interface(IStringList)
    [cstrGUID_IStringStringList]
    function AddString(const strKey: string; const strValue: string): integer;
    function Extract(const strValue: string): string;
    function GetString(const intIndex: Integer): string;
    procedure PutString(const intIndex: Integer; const strValue: string);
  end;

  IStringRealList = interface(IStringList)
    [cstrGUID_IStringRealList]
    function AddReal(const strKey: string; const realValue: double): integer;
    function GetReal(const intIndex: Integer): double;
    procedure SetReal(const intIndex: Integer; const realValue: double);
  end;

  IIntegerInterfaceList = interface(IIntegerList)
    [cstrGUID_IIntegerInterfaceList]
    function AddInterface(const intKey: integer; const ref: IInterface): Integer;
    function GetInterface(const intIndex: Integer): IInterface;
    function IndexOfInterface(const ref: IInterface): Integer;
    procedure InsertInterface(const intIndex: Integer; const intNumber: integer; const AInterface:
      IInterface);
    procedure PutInterface(const intIndex: Integer; const ref: IInterface);
  end;

  IIntegerIntegerList = interface(IIntegerList)
    [cstrGUID_IIntegerIntegerList]
    function AddInt(const intKey: integer; const intValue: integer): integer;
    function GetInt(const intIndex: Integer): integer;
    function IndexOfInt(const intValue: integer): integer;
    procedure PutInt(const intIndex: integer; const intValue: integer);
  end;

  IIntegerRealList = interface(IIntegerList)
    [cstrGUID_IIntegerRealList]
    function AddReal(const intKey: integer; const realValue: double): integer;
    function GetReal(const intIndex: Integer): double;
    function IndexOfReal(const realValue: double): integer;
    procedure PutReal(const intIndex: integer; const realValue: double);
  end;

  IIntegerStringList = interface(IIntegerList)
    [cstrGUID_IIntegerStringList]
    function AddString(const intKey: integer; const strValue: string): Integer;
    function GetString(const intIndex: Integer): string;
    function IndexOfString(const strValue: string): Integer;
    procedure InsertString(const intIndex: Integer; const intNumber: integer; const AString: string);
    procedure PutString(const intIndex: Integer; const strValue: string);
  end;

  IRealInterfaceList = interface(IRealList)
    [cstrGUID_IRealInterfaceList]
    function AddInterface(const realKey: double; const ref: IInterface): Integer;
    function GetInterface(const intIndex: Integer): IInterface;
    function IndexOfInterface(const ref: IInterface): Integer;
    procedure InsertInterface(const intIndex: Integer; const realNumber: double; const AInterface:
      IInterface);
    procedure PutInterface(const intIndex: Integer; const ref: IInterface);
  end;

  IKeypoint = interface
    [cstrGUID_IKeypoint]
    function Clone: IKeypoint;
    function GetPosition: double;
    procedure Assign(const other: IInterface);
    procedure SetPosition(const Value: double);
  end;

  ISimpleKeypoint = interface(IKeypoint)
    [cstrGUID_ISimpleKeypoint]
    function GetValue: double;
    procedure SetValue(const Value: double);
  end;

  IKeypointList = interface(IList)
    [cstrGUID_IKeypointList]
    function GetKeyByIndex(const intIndex: integer): IKeypoint;
    function GetLastKeyIndex: integer;
    function InsertKey(const realPosition: double; const key: IKeypoint): integer;
    procedure SetKey(const realBegin, realEnd: double; const key: IKeypoint);
  end;

  IImageConverter = interface
    [cstrGUID_IImageConverter]
    function GetSupportedInputFormats: TImageFormats;
    function GetSupportedOutputFormats: TImageFormats;
    function GetTempPath: string;
    procedure ConvertImage(const input: IStreamReader; const enumInFormat: TImageFormat; const output:
      IStreamWriter; const enumOutFormat: TImageFormat); overload;
    procedure SetTempPath(const strPath: string);
  end;

  IGraphicsProviderHost = interface
    [cstrGUID_IGraphicsProviderHost]
    procedure EnumerateGraphic(const query: IGraphicsDescription; const intUID: integer; const strAttributes:
      string; const enumFormat: TImageFormat);
  end;

  IGraphicsProvider = interface
    [cstrGUID_IGraphicsProvider]
    procedure RequestGraphic(const host: IGraphicsProviderHost; const desc: IGraphicsDescription);
    procedure FetchGraphic(const intUID: integer; out reader: IStreamReader);
  end;

  IClassicImageProvider = interface
    [cstrGUID_IClassicImageProvider]
    function GetBitmap(const intID: integer; const nvInfo: IStringList): cardinal; overload;
    function GetBitmap(const strName: string; const nvInfo: IStringList): cardinal; overload;
    function GetIDByName(const strName: string): integer;
    function GetImage(const intID: integer): cardinal;
    function SaveImgToFile(const intID: integer; var strFileName: string): boolean;
    procedure ClearItem(const intID: integer); overload;
    procedure ClearItem(const strName: string); overload;
    procedure DrawBitmap(const Canvas: cardinal; const intX, intY, intID: integer; const intWidth: integer =
      16; const intHeight: integer = 16; const blnTransparent: boolean = true); overload;
    procedure DrawBitmap(const Canvas: cardinal; const intX, intY: integer; const strName: string; const
      intWidth: integer = 16; const intHeight: integer = 16; const blnTransparent: boolean = true); overload;
    procedure GetBitmapSize(const intID: integer; var intW, intH: integer); overload;
    procedure GetBitmapSize(const strName: string; var intW, intH: integer); overload;
  end;

  IDocumentConverter = interface
    [cstrGUID_IDocumentConverter]
    function CanConvert(const enumInFormat, enumOutFormat: TDocumentFormat; out strReason: string): boolean;
      overload;
    function MapFileExtensionToFormat(const strFileExtension: string): TDocumentFormat;
    function MapFormatToFileExtension(const enumFormat: TDocumentFormat): string;
    procedure ConvertDocument(const InStream: IStreamlet; const enumInFormat: TDocumentFormat; const
      OutStream: IStreamlet; const OutFormat: TDocumentFormat); overload;
    procedure SetLicenseKey(const strKey: WideString);
    procedure SetTimeOuts(const InitializationTimeout: Integer; const PageConversionTimeout: Integer; const FileConversionTimeout: Integer );
  end;

  IExternalProcess = interface
    [cstrGUID_IExternalProcess]
    function IsBusy: boolean;
  end;

  ICursorManager = interface
    [cstrGUID_ICursorManager]
    procedure AbortLock;
    procedure RequestLock(const crLockCursor: TCursor = crSQLWait);
    procedure RevokeLock;
    procedure SetExternalProcess(const ExternalProcess: IExternalProcess);
    procedure SetModeImmediateUpdate(const blnValue: boolean);
    procedure Disable;
    procedure Enable;
  end;

  IModuleInteraction = interface
    [cstrGUID_IModuleInteraction]
    function GetModuleUID: string;
    function GetInteractionControl: TModuleInteractionControl;
    function GetInteractionState: TModuleInteractionState;
    procedure SetModuleUID(const strModuleUID: string);
    procedure SetInteractionControl(const interactionControl: TModuleInteractionControl);
    procedure SetInteractionState(const interactionState: TModuleInteractionState);
  end;

  IMessage = interface
    [cstrGUID_IMessage]
    function Clone: IMessage;
    function GetRepresentation: string;
    function GetType: integer;
    procedure Assign(const other: IInterface);
  end;

  IMAttributeChange = interface(IMessage)
    [cstrGUID_IMAttributeChange]
    function GetAttributeName: string;
    procedure SetAttributeName(const strName: string);
  end;

  IMModuleInvocation = interface(IMessage)
    [cstrGUID_IMModuleInvocation]
    function GetModuleInteraction: IModuleInteraction;
    function GetContext: IInterface;
    procedure SetModuleInteraction(const mi: IModuleInteraction);
    procedure SetContext(const context: IInterface);
  end;

  IMCode = interface(IMessage)
    [cstrGUID_IMCode]
    function GetCode: string;
    procedure SetCode(const strCode: string);
  end;

  IMCodedPayload = interface(IMCode)
    [cstrGUID_IMCodedPayload]
    function GetPayload: IInterface;
    procedure SetPayload(const payload: IInterface);
  end;

  IMPromptYesNo = interface(IMessage)
    [cstrGUID_IMPromptYesNo]
    function GetAffirmative: boolean;
    function GetPrompt: string;
    procedure SetAffirmative(const blnValue: boolean);
    procedure SetPrompt(const strPrompt: string);
  end;

  IMNumericalAttributeParameters = interface(IMessage)
    [cstrGUID_IMNumericalAttributeParameters]
    function GetEnableMaximum: boolean;
    function GetEnableMinimum: boolean;
    function GetIncrement: double;
    function GetMaximum: double;
    function GetMinimum: double;
    function GetName: string;
    procedure SetEnableMaximum(const blnEnabled: boolean);
    procedure SetEnableMinimum(const blnEnabled: boolean);
    procedure SetIncrement(const realInc: double);
    procedure SetMaximum(const realMax: double);
    procedure SetMinimum(const realMin: double);
    procedure SetName(const strName: string);
  end;

  IMessageFactory = interface
    [cstrGUID_IMessageFactory]
    function CreateMessage(const intMessageType: integer): IMessage;
    procedure RegisterMessagePrototype(const intMessageType: integer; const msg: IMessage);
  end;

  IObservable = interface
    [cstrGUID_IObservable]
    procedure AddObserver(const observer: IObserver);
    procedure RemoveObserver(const observer: IObserver);
  end;

  IObserver = interface
    [cstrGUID_IObserver]
    procedure AddObservable(const observable: IObservable);
    procedure RemoveObservable(const observable: IObservable);
    procedure HandleMessage(const sender: IObservable; const intMessageType: integer; const msg: IMessage);
  end;

  ISegmentListener = interface
    [cstrGUID_ISegementListener]
    procedure ReceiveSegment(const realLowValue, realHighValue: double);
    procedure RemoveSegment(const realLowValue, realHighValue: double);
    procedure ReceiveSegmentInUse(const realLowValue, realHighValue: double);
  end;

  ILinearAccumulator = interface
    [cstrGUID_ILinAcc]
    procedure AddRange(realLowValue: double; realHighValue: double; const SegmentListener: ISegmentListener);
    procedure SubtractRange(realLowValue: double; realHighValue: double; const SegmentListener:
      ISegmentListener);
    procedure Clear;
  end;

  ITraceHost = interface
    [cstrGUID_ITraceHost]
    function GetApplicationName: string;
    function GetExecutableName: string; 
    function GetModuleChar: Char; 
    function GetRootPath: string; 
    function GetTitle: string;
    function ReadyForIndirectTrace: boolean; // -> IIndirectTraceHost
    function TraceNow: TDateTime;
    procedure IndirectTrace(const trcLevel: integer; const trcMessage: string); // -> IIndirectTraceHost
    procedure ReconnectNotification(const Text: string); 
  end;

  IDataElement = interface
    [cstrGUID_IDataElement]
    function Clone_DE(const controller: IInterface): IDataElement;
    function GetIdentifier_DE(const controller: IInterface): string;
    function GetType_DE(const controller: IInterface): TDataElementType;
    procedure Assign(const controller: IInterface; const value: IInterface);
    procedure SetIdentifier_DE(const controller: IInterface; const Value: string);
    function GetPosition_DE(const controller: IInterface): double;
    function HasAdditionalData_DE(const controller: IInterface): boolean;
  end;

  ISimpleDataElement = interface(IDataElement)
    [cstrGUID_ISimpleDataElement]
    function GetValue_DE(const controller: IInterface): double;
    function GetPosition_DE(const controller: IInterface): double;
    procedure SetPosition_DE(const controller: IInterface; const realValue: double);
    procedure SetValue_DE(const controller: IInterface; const realValue: double);
  end;

  IRangeDataElement = interface(IDataElement)
    [cstrGUID_IRangeDataElement]
    function GetPosition_DE(const controller: IInterface): double;
    function GetValue1_DE(const controller: IInterface): double;
    function GetValue2_DE(const controller: IInterface): double;
    procedure SetPosition_DE(const controller: IInterface; const realValue: double);
    procedure SetValue1_DE(const controller: IInterface; const realValue: double);
    procedure SetValue2_DE(const controller: IInterface; const realValue: double);
  end;

  IGanttDataElement = interface(IDataElement)
    [cstrGUID_IGanttDataElement]
    function GetPosition1_DE(const controller: IInterface): double;
    function GetPosition2_DE(const controller: IInterface): double;
    function GetValue_DE(const controller: IInterface): double;
    procedure SetPosition1_DE(const controller: IInterface; const realValue: double);
    procedure SetPosition2_DE(const controller: IInterface; const realValue: double);
  end;

  IQualitativeDataElement = interface(IGanttDataElement)
    [cstrGUID_IQualitativeDataElement]
    function GetTextValue_DE(const controller: IInterface): string;
    procedure SetTextValue_DE(const controller: IInterface; const strValue: string);
  end;

  ITextDataElement = interface(IDataElement)
    [cstrGUID_ITextDataElement]
    function GetValue_DE(const controller: IInterface): string;
    function GetPosition_DE(const controller: IInterface): double;
    procedure SetPosition_DE(const controller: IInterface; const realValue: double);
    procedure SetValue_DE(const controller: IInterface; const strValue: string);
  end;

  ISAXCommon = interface
    procedure Close;
    procedure OpenFile(const strFilename: string);
    function GetEncoding: TXMLEncoding;
    function GetProcessingOptions: TSAXProcessingOptions;
    procedure SetProcessingOptions(const Value: TSAXProcessingOptions);
  end;

  ISAXReader = interface(ISAXCommon)
    [cstrGUID_ISAXReader]
    procedure Read(const eventHandler: IInterface; const enumReadOptions: TSAXProcessingOptions = [poTrim,
      poRecode]);
    procedure OpenStream(const reader: IStreamReader);
    procedure OpenString(const strData: string);
  end;

  ISAXWriter = interface(ISAXCommon)
    [cstrGUID_ISAXWriter]
    procedure OpenStream(const writer: IStreamWriter);
    procedure WriteAttribute(const strAttributeName, strAttributeValue: string);
    procedure WriteComment(const strText: string);
    procedure WriteElementEnd(const strElementName: string);
    procedure WriteElementStart(const strElementName: string);
    procedure WriteText(const strText: string);
    procedure WriteProcessingInstruction(const strTarget, strData: string);
    procedure WriteDocType(const strName, strPublicID, strSystemID: string);
  end;

  ISAXElementHandler = interface
    [cstrGUID_ISAXElementHandler]
    procedure ReceiveElementEnd_SAX(const sender: ISAXReader; const strElementName: string);
    procedure ReceiveElementStart_SAX(const sender: ISAXReader; const strElementName: string);
    procedure ReceiveText_SAX(const sender: ISAXReader; const strText: string);
    procedure ReceiveElementAttribute_SAX(const sender: ISAXReader; const strAttributeName, strAttributeValue:
      string);
  end;

  ISAXCommentHandler = interface
    [cstrGUID_ISAXCommentHandler]
    procedure ReceiveComment_SAX(const sender: ISAXReader; const strText: string);
  end;

  ISAXProcessingInstructionHandler = interface
    [cstrGUID_ISAXProcessingInstructionHandler]
    procedure ReceiveProcessingInstructionStart_SAX(const sender: ISAXReader; const strTarget: string);
    procedure ReceiveProcessingInstructionEnd_SAX(const sender: ISAXReader);
    procedure ReceivePIAttribute_SAX(const sender: ISAXReader; const strAttributeName, strAttributeValue:
      string);
  end;

  ITimer = interface
    [cstrGUID_ITimer]
    procedure SetDelay(const intIndex: integer);
  end;

  ITimerHost = interface
    [cstrGUID_ITimerHost]
    procedure TimeTick_TH(const sender: ITimer);
  end;

  ILogHost = interface
    [cstrGUID_ILogHost]
    procedure LogMessage(const sender: ILogClient; const strMessage: string);
  end;

  ILogClient = interface
    [cstrGUID_ILogClient]
  end;

  IEnumerationValue = interface
    [cstrGUID_IEnumerationValue]
    function GetCode: string;
    function GetLabel(const strKey: string): string;
    function GetOrdinal: integer;
    procedure Initialize(const intOrdinal: integer; const strCode: string);
    procedure SetLabel(const strKey, strValue: string);
  end;

  IEnumeration = interface
    [cstrGUID_IEnumeration]
    function AddValue(const intOrdinal: integer; const strCode: string): IEnumerationValue; overload;
    function AddValue(const intOrdinal: integer; const strCode: string; const strLabels: array of string):
      IEnumerationValue; overload;
    function FindValue(const value: IEnumerationValue; out intIndex: integer): boolean;
    function FindValueByCode(const strCode: string; out value: IEnumerationValue): boolean;
    function FindValueByOrdinal(const intOrdinal: integer; out value: IEnumerationValue): boolean;
    function GetLastValueIndex: integer;
    function GetValueByIndex(const intIndex: integer): IEnumerationValue;
    function GetValueByOrdinal(const intOrdinal: integer): IEnumerationValue;
    function GetValueCount: integer;
    procedure Clear;
  end;

  ISATableData = interface
    [cstrGUID_ISATableData]
    function EOF_STD: boolean;
    function GetLastColumnIndex_STD: integer;
    function GetTitleByColumnIndex_STD(const intColumnIndex: integer): string;
    function GetTypeByColumnIndex_STD(const intColumnIndex: integer): string;
    function GetValueByColumnIndex_STD(const intColumnIndex: integer): variant;
    procedure Next_STD;
  end;

  IRATableData = interface
    [cstrGUID_IRATableData]
    procedure SetCellValue_RTD(const intColumnIndex, intRowIndex: integer; const varValue: variant);
    function GetCellValue_RTD(const intColumnIndex, intRowIndex: integer): variant;
    procedure ConfigureColumn_RTD(const intColumnIndex: integer; const strTitle, strType: string);
  end;

  ITableWriter = interface
    [cstrGUID_ITableWriter]
    procedure WriteToStream_TW(const writer: IStreamWriter; const data: ISATableData);
    procedure WriteToFile_TW(const strFilename: string; const data: ISATableData);
  end;

  ITableReader = interface
    [cstrGUID_ITableReader]
    procedure ReadFromStream_TR(const reader: IStreamReader; const data: IRATableData);
    procedure ReadFromFile_TR(const strFilename: string; const data: IRATableData);
  end;

  ITempFile = interface
    [cstrGUID_ITempFile]
    function GetFileName: string;
    function GetStream: IStreamlet;
    procedure Release;
    procedure Reopen;
  end;

  ITextRepresentation = interface
    [cstrGUID_ITextRepresentation]
    function GetText_TR: string;
  end;

  ISilentPrinter = interface
    [cstrGUID_ISilentPrinter]
    procedure PrintStream(const stream: IStreamlet);
  end;

  IStyle = interface
    [cstrGUID_IStyle]
    function ApplyMarginTo(const ARect: TIntegerRect): TIntegerRect;
    function ApplyPaddingTo(const ARect: TIntegerRect): TIntegerRect;
    function CloneStyle: IStyle;
    function GetBackgroundColor: TRGBColor;
    function GetBottomMargin: integer;
    function GetBottomPadding: integer;
    function GetFontColor: TRGBColor;
    function GetFontName: string;
    function GetFontSize: integer;
    function GetFontStyles: TDocFontStyles;
    function GetHAlignment: THorizontalTextAlignment;
    function GetStyleName: string;
    function GetTopMargin: integer;
    function GetTopPadding: integer;
    function GetVAlignment: TVerticalTextAlignment;
    function HasBackground: boolean;
    function HasFontSize: boolean;
    function HasFontStyles: boolean;
    function HasFontColor: boolean;
    procedure AddFontFamily(const strFamilyName: string);
    procedure ApplyTo(const style: IStyle);
    procedure ClearFontFamilies;
    procedure ClearFontStyles;
    procedure IncludeFontStyle(const style: TDocFontStyle);
    procedure SetBackground(const blnVisible: boolean; const colFeature: TRGBColor);
    procedure SetBorder(const intIndex: integer; const blnVisible: boolean; const colFeature: TRGBColor);
    procedure SetFontColor(const intColor: TRGBColor);
    procedure SetFontSize(const intValue: integer);
    procedure SetFontStyles(const Value: TDocFontStyles);
    procedure SetHAlignment(const enumValue: THorizontalTextAlignment);
    procedure SetMarginRect(const MarginRect: TIntegerRect);
    procedure SetPaddingRect(const PaddingRect: TIntegerRect);
    procedure SetStyleName(const strValue: string);
    procedure SetVAlignment(const enumValue: TVerticalTextAlignment);
  end;

  IDocumentWriter = interface
    [cstrGUID_IDocumentWriter]
    function BeginDocument: IStreamlet;
    function CreateStyle: IStyle;
    procedure BeginParagraph(const paragraphStyle: IStyle);
    procedure EndDocument;
    procedure EndParagraph;
    procedure FinalizeConfiguration;
    procedure WriteBreak;
    procedure WriteText(const strPlainText: string);
  end;

  IDocumentReader = interface
    [cstrGUID_IDocumentReader]
    procedure ReadFromStream(const stream: IStreamlet; const writer: IDocumentWriter);
  end;

  ISpellChecker = interface
    [cstrGUID_ISpellChecker]
    function CheckContent(const objControl: TObject): boolean;
    function CheckSynonyms(const objControl: TObject): boolean;
    function GetContent: string;
    function GetDocumentFormat: TDocumentFormat;
    function GetShowFocusWarning: boolean;
    function GetSynonyms: string;
    function GetProvidesThesaurus: boolean;
    procedure OpenThesaurus(const objControl: TObject);
    procedure ReplaceSynonym(const objControl: TObject; const intSynonymIndex: integer);
    procedure SetContent(const strContent: string);
    procedure SetDocumentFormat(const enumDocFormat: TDocumentFormat);
    procedure SetProvidesThesaurus(const Value: boolean);
    procedure ShowWord(const bringToFront: boolean);
    procedure HideWord;
    property enumDocumentFormat: TDocumentFormat read GetDocumentFormat write SetDocumentFormat;
    property strDocumentContent: string read GetContent write SetContent;
    property strSynonyms: string read GetSynonyms;
    property blnShowFocusWarning: boolean read GetShowFocusWarning;
    property ProvidesThesaurus: boolean read GetProvidesThesaurus write SetProvidesThesaurus;
  end;

  ICryptEngine = interface
    [cstrGUID_ICryptEngine]
    function CryptStringAsym(const strData: string): string;
    function CryptStringSym(const strKey: string; const strData: string): string;
    function DecryptStringAsym(const strData: string): string;
    function DecryptStringSym(const strKey: string; const strData: string): string;
    function GetIdentifier: string;
    function GetPublicKey: string;
    function HashString(const strData: string): string;
    function SignString(const strData: string): string;
    function VerifySignature(const strData, strSignerPublicKey, strSignature: string): boolean;
    function MD5HashBuffer(const data: Pointer; const intSize: integer): string; 
    function UnixHash(const Source: string): integer; 
    procedure GenerateKeys;
  end;

  IStopWatch = interface
    [cstrGUID_IStopWatch]
    procedure Start;
    function StopAndGetLabel: string;
    function StopAndGetSeconds: double;
  end;

  IType = interface
    [cstrGUID_IType]
    function GetName: string;
    procedure SetName(const strName: string);
    function GetInstanceSize: integer;
    function IsCharacterType: boolean;
    function IsNumericType: boolean;
    function IsIndirectType: boolean;
  end;

  IFunctionType = interface(IType)
    [cstrGUID_IFunctionType]
    function GetLastParameterIndex: integer;
    function GetOffset: LongWord;
    procedure CreateParameter(const strName: string; const typeDef: IType);
    procedure GetParameterByIndex(const intIndex: integer; out strName: string; out typeDef: IType);
    procedure SetOffset(const intOffset: LongWord);
    procedure SetReturnType(const typeDef: IType);
  end;

  ISimpleType = interface(IType)
    [cstrGUID_ISimpleType]
    function GetIsArray: boolean;
    function GetKind: TDataTypeKind;
    procedure SetIsArray(const blnValue: boolean);
    procedure SetKind(const enumKind: TDataTypeKind);
  end;

  ICompositeType = interface(IType)
    [cstrGUID_ICompositeType]
    function GetLastTypeIndex: integer;
    function GetTypeByIndex(const intIndex: integer): IType;
    procedure AddField(const strName: string; const typeDef: IType);
    procedure GetFieldOffsetByName(const strName: string; out intOffset: cardinal; out fieldType: IType);
  end;

  IInvocableType = interface(IType)
    [cstrGUID_IInvocableType]
    function CreateMethod(const strName: string): IFunctionType;
  end;

  ITypeCollection = interface
    [cstrGUID_ITypeCollection]
    function GetType(const strName: string): IType;
    function ContainsType(const strName: string; out typeDef: IType): boolean;
    procedure RegisterType(const typeDef: IType);
    function CreateSimpleType(const strName: string): ISimpleType;
    function CreateCompositeType(const strName: string): ICompositeType;
    function CreateClassType(const strName: string): IInvocableType;
    function CreateInterfaceType(const strName: string): IInvocableType;
  end;

  ISymbol = interface
    [cstrGUID_ITypeInstance]
    function GetAddress: Pointer;
    function GetImmediateValue: double;
    function GetIsArray: boolean;
    function GetIsConstant: boolean;
    function GetName: string;
    function GetSize: integer;
    function GetType: IType;
    procedure SetAddress(const ptrAddress: Pointer);
    procedure SetImmediateValue(const realValue: double);
    procedure SetIsArray(const blnArray: boolean);
    procedure SetIsConstant(const blnValue: boolean);
    procedure SetName(const strName: string);
    procedure SetType(const typeDef: IType);
    function GetIsLocal: boolean;
    procedure SetIsLocal(const blnValue: boolean);
    procedure SetOffset(const intOffset: integer);
    function GetOffset: integer;
    function GetOwner: IInterface;
    procedure SetOwner(const owner: IInterface);
    procedure Release;
  end;

  ITypedStorage = interface
    [cstrGUID_ITypedStorage]
    function AllocateSymbol(const typeInst: IType; const blnArray: boolean): pointer;
    function CalculateAbsoluteAddress(const intOffset: LongWord): Pointer;
    function CalculateOffset(const ptrBlock: pointer): longword;
    function GetInteger(const ptrBlock: pointer; const typeDef: IType): integer; overload;
    function GetInterface(const ptrBlock: pointer; const typeDef: IType): IInterface; overload;
    function GetLastBlockIndex: integer;
    function GetReal(const ptrBlock: pointer; const typeDef: IType): double; overload;
    function GetString(const ptrBlock: pointer; const typeDef: IType): string; overload;
    function GetType(const ptrInstance: pointer): IType;
    procedure DescribeContents(const Lines: IStringInterfaceList);
    procedure DisposeAll;
    procedure DisposeInstance(const ptrInstance: pointer);
    procedure GetBlockByIndex(const intIndex: integer; out ptrBlock: Pointer; out typeDef: IType);
    procedure GetData(out ptrData: pointer; out intSize: cardinal);
    procedure ResolveField(const ptrInstance: pointer; const strFieldName: string; out ptrBlock: pointer; out
      fieldType: IType);
    procedure SetData(const ptrData: pointer; const intSize: cardinal);
    procedure SetInteger(const ptrBlock: pointer; const typeDef: IType; const intValue: integer); overload;
    procedure SetInterface(const ptrBlock: pointer; const typeDef: IType; const Value: IInterface); overload;
    procedure SetReal(const ptrBlock: pointer; const typeDef: IType; const realValue: double); overload;
    procedure SetString(const ptrBlock: pointer; const typeDef: IType; const strValue: string); overload;
  end;

  IHashTable = interface
    function GetKeyCollection: IStringList;
    function ContainsKey(const strKey: string): boolean;
    function GetCapacity: integer;
    function GetNumElements: integer;
    function GetNumKeys: integer;
    function IsEmpty: boolean;
    function Remove(const strKey: string): boolean;
    procedure Clear;
  end;

  IStringHashTable = interface(IHashTable)
    [cstrGUID_IStringHashTable]
    function Lookup(const strKey: string; out strValue: string): boolean;
    procedure Add(const strKey, strValue: string);
  end;

  IIntegerHashTable = interface(IHashTable)
    [cstrGUID_IIntegerHashTable]
    function Lookup(const strKey: string; out intValue: integer): boolean;
    procedure Add(const strKey: string; const intValue: integer);
  end;

  IInterfaceHashTableIterator = interface
    [cstrGUID_IInterfaceHashTableIterator]
    function GetNext(out elem: IInterface): boolean;
  end;

  IInterfaceHashTable = interface(IHashTable)
    [cstrGUID_IInterfaceHashTable]
    function GetElementCollection: IInterfaceList;
    function GetHashSize: integer;
    function Lookup(const strKey: string; out Value: IInterface): boolean;
    function LookupNext(out Value: IInterface): boolean;
    procedure Add(const strKey: string; const Value: IInterface);
    procedure Append(const strKey: string; const Value: IInterface);
  end;

  ISourceParserHost = interface
    [cstrGUID_ISourceParserHost]
    procedure RecordParsed(const parser: ISourceParser);
  end;

  ISourceParser = interface
    [cstrGUID_ISourceParser]
    function GetCaseConvert: TConversionType;
    function GetDelimiterOptions: TDelimiterOptions;
    function GetErrorCode: integer;
    function GetFieldBreaks: string;
    function GetFieldCount: integer;
    function GetFieldDelimiters: string;
    function GetFieldLength: integer;
    function GetHost: ISourceParserHost;
    function GetParseField: string;
    function GetParseList: IStringList;
    function GetPercentComplete: integer;
    function GetRecordCount: integer;
    function GetRecordDelimiters: string;
    function GetSource: string;
    function GetTag: integer;
    function GetTextQualifiers: string;
    function GetWhitespaceOptions: TWhitespaceOptions;
    procedure AbortParse;
    procedure ParseMemory;
    procedure SetCaseConvert(const enumType: TConversionType);
    procedure SetDelimiterOptions(const enumsOptions: TDelimiterOptions);
    procedure SetFieldBreaks(const strBreaks: string);
    procedure SetFieldDelimiters(const strDelimiters: string);
    procedure SetHost(const host: ISourceParserHost);
    procedure SetRecordDelimiters(const strDelimiters: string);
    procedure SetSource(const strData: string);
    procedure SetTag(const intTag: integer);
    procedure SetTextQualifiers(const strQualifiers: string);
    procedure SetWhitespaceOptions(const enumsOptions: TWhitespaceOptions);
  end;

  IInstanceMonitor = interface
    [cstrGUID_IInstanceMonitor]
    procedure ListInstanceCounts(const list: IStringIntegerList);
  end;

  IStreamingFactory = interface
    [cstrGUID_IStreamingFactory]
    function CreateBufferedFileStreamlet(const strFilename: string; const enumMode: TFileAccessMode; const
      intBufferSize: integer = 102400): IStreamlet;
    function CreateMemoryStreamlet(const intInitialSize: integer = 102400): IStreamlet; overload;
    function CreateMemoryStreamlet(const strData: string; const intInitialSize: integer = 102400): IStreamlet; overload;
    function CreateRAMStreamlet: IStreamlet;
    function CreateResourceStreamletFromID(const Instance: THandle; const intResID: integer; const
      enumResKind: TResourceKind): IStreamlet;
    function CreateResourceStreamletFromName(const Instance: THandle; const strResName: string; const
      enumResKind: TResourceKind): IStreamlet;
    function CreateZLibReader(const source: IStreamReader): IStreamReader;
    function CreateZLibWriter(const target: IStreamWriter; const enumGoal: TCompressionGoal = cgQuality):
      IStreamWriter;
    function CreateRijndaelReader(const source: IStreamReader; const strKey: string): IStreamReader;
    function CreateRijndaelWriter(const target: IStreamWriter; const strKey: string): IStreamWriter;
    function GetInstanceMonitor: IInstanceMonitor;
  end;

  IListFactory = interface
    [cstrGUID_IListFactory]
    function CreateIntegerHashTable(const intTableSize: integer): IIntegerHashTable;
    function CreateIntegerInterfaceList: IIntegerInterfaceList;
    function CreateIntegerList: IIntegerList;
    function CreateIntegerIntegerList: IIntegerIntegerList;
    function CreateIntegerRealList: IIntegerRealList;
    function CreateIntegerStringList: IIntegerStringList;
    function CreateInterfaceHashTable(const intTableSize: integer): IInterfaceHashTable;
    function CreateInterfaceList: IInterfaceList;
    function CreateKeypointList: IKeypointList;
    function CreateRealInterfaceList: IRealInterfaceList;
    function CreateStringHashTable(const intTableSize: integer): IStringHashTable;
    function CreateStringIntegerList: IStringIntegerList;
    function CreateStringInterfaceList: IStringInterfaceList;
    function CreateStringList: IStringList;
    function CreateStringListReplaceSystemText: IStringListReplaceSystemText;
    function CreateStringRealList: IStringRealList;
    function CreateStringStringList: IStringStringList;
    function GetInstanceMonitor: IInstanceMonitor;
  end;

  IGraphicsDescription = interface
    [cstrGUID_IGraphicsDescription]
    function GetFacetName: string;
    function GetFlag(const strFlagName: string): boolean;
    function GetFlagByIndex(const intIndex: integer): string;
    function GetLastFlagIndex: integer;
    function GetResourceName: string;
    function GetTargetColorDepth: TColorDepth;
    function GetTargetResolution: double;
    procedure SetFlag(const strFlagName: string; const blnState: boolean);
    procedure SetResourceName(const strName: string);
    procedure SetTargetColorDepth(const enumDepth: TColorDepth);
    procedure SetTargetResolution(const realResolution: double);
    procedure ClearFlags;
  end;

  IIni = interface
    [cstrGUID_IIni]
    function GetText: string;
    function ReadBinaryStream(const strSection, strName: string; const stream: IStreamlet): Integer;
    function ReadBool(const strSection, strIdent: string; const blnDefault: Boolean): Boolean;
    function ReadDate(const strSection, strName: string; const realDefault: TDateTime): TDateTime;
    function ReadDateTime(const strSection, strName: string; const realDefault: TDateTime): TDateTime;
    function ReadFloat(const strSection, strName: string; const realDefault: Double): Double;
    function ReadInteger(const strSection, strIdent: string; const intDefault: integer): integer; overload;
    function ReadInteger(const strSection, strIdent: string): integer; overload;
    function ReadString(const strSection, strIdent, strDefault: string): string; overload;
    function ReadString(const strSection, strIdent: string): string; overload;
    function ReadTime(const strSection, strName: string; const realDefault: TDateTime): TDateTime;
    function RenameSection(const strCurrentName, strNewName: string): boolean;
    function SectionExists(const strSection: string): boolean;
    function ValueExists(const strSection, strIdent: string): Boolean;
    procedure Append(const ini: IIni); 
    procedure Clear;
    procedure DeleteKey(const strSection, strIdent: string);
    procedure EraseSection(const strSection: string);
    procedure ReadSection(const strSection: string; const strings: IStringList);
    procedure ReadSections(const strings: IStringList);
    procedure ReadSectionValues(const strSection: string; const strings: IStringList);
    procedure SetText(const strText: string);
    procedure UpdateFile;
    procedure WriteBinaryStream(const strSection, strName: string; const stream: IStreamlet);
    procedure WriteBool(const strSection, strIdent: string; const blnValue: Boolean);
    procedure WriteDate(const strSection, strName: string; const realValue: TDateTime);
    procedure WriteDateTime(const strSection, strName: string; const realValue: TDateTime);
    procedure WriteFloat(const strSection, strName: string; const realValue: Double);
    procedure WriteInteger(const strSection, strIdent: string; const intValue: integer);
    procedure WriteString(const strSection, strIdent, strValue: string);
    procedure WriteTime(const strSection, strName: string; const realValue: TDateTime);
  end;

  IIniFactory = interface
    [cstrGUID_IIniFactory]
    function CreateIniFile(const strFilename: string): IIni;
    function CreateVirtualIni(const strIniData: string): IIni;
  end;

  IAxisDefinition = interface
    [cstrGUID_IAxisDefinition]
    function GetAlignment: TPlanarAlignment;
    function GetAutoFit: boolean;
    function GetDynamicExtent: boolean;
    function GetEnd: double;
    function GetExtent: double;
    function GetInverted: boolean;
    function GetLabel: string;
    function GetManualScale: double;
    function GetStart: double;
    function GetStyle: TAxisStyle;
    function GetSubdivisionCount: integer;
    function GetSubdivisionLimit: integer;
    function GetSubscaleCount: integer;
    function GetSubscaleExtent: double;
    function GetType: TAxisType;
    function GetUnit: string;
    function GetVisible: boolean;
    procedure SetAlignment(const enumAlignment: TPlanarAlignment);
    procedure SetAutoFit(const blnAutoFit: boolean);
    procedure SetDynamicExtent(const blnValue: boolean);
    procedure SetEnd(const realEnd: double);
    procedure SetExtent(const realExtent: double);
    procedure SetLabel(const strLabel: string);
    procedure SetManualScale(const realScale: double);
    procedure SetStart(const realStart: double);
    procedure SetStyle(const enumStyle: TAxisStyle);
    procedure SetSubdivisionCount(const intCount: integer);
    procedure SetSubdivisionLimit(const intLimit: integer);
    procedure SetSubscaleCount(const intCount: integer);
    procedure SetSubscaleExtent(const realExtent: double);
    procedure SetType(const enumType: TAxisType);
    procedure SetUnit(const strUnit: string);
    procedure SetVisible(const blnVisible: boolean);
    function GetIdentifier: string;
    procedure SetIdentifier(const strIdentifier: string);
    procedure SetInverted(const blnInverted: boolean);
    function GetManipulationMode: TAxisManipulationMode;
    procedure SetManipulationMode(const enumMode: TAxisManipulationMode);
    procedure SetSubdivision(const intIndex: integer; const intValue: integer);
    function GetSubdivision(const intIndex: integer): integer;
    function GetOrigin: double;
    procedure SetOrigin(const realValue: double);
  end;

  ISeriesDefinition = interface
    [cstrGUID_ISeriesDefinition]
    function GetDisplayMode: TDisplayMode;
    function GetLineStyle: TSeriesLineStyle;
    function GetLineWidth: double;
    function GetMarkKind: TMarkKind;
    function GetOrdinate: IAxisDefinition;
    function GetSeriesColor: TRGBColor;
    function GetSeriesName: string;
    function GetSeriesType: TDataElementType;
    function GetShadowColor: TRGBColor;
    function GetTickShape: TTickShape;
    function GetWantsShadow: boolean;
    function GetIdentifier: string;
    function GetMarkSize: double;
    function GetValueFormat: string;
    function GetDisplayOptional: boolean;
  end;

  ISeriesDefinitionFriend = interface(ISeriesDefinition)
    [cstrGUID_ISeriesDefinitionFriend]
    procedure SetDisplayMode(const enumMode: TDisplayMode);
    procedure SetLineStyle(const enumStyle: TSeriesLineStyle);
    procedure SetLineWidth(const realWidth: double);
    procedure SetMarkKind(const enumKind: TMarkKind);
    procedure SetOrdinate(const ordinate: IAxisDefinition);
    procedure SetSeriesColor(const colSeries: TRGBColor);
    procedure SetSeriesName(const strName: string);
    procedure SetSeriesType(const enumType: TDataElementType);
    procedure SetShadowColor(const colShadow: TRGBColor);
    procedure SetTickShape(const enumShape: TTickShape);
    procedure SetWantsShadow(const blnShadow: boolean);
    procedure SetIdentifier(const strIdentifier: string);
    procedure SetMarkSize(const realSize: double);
    procedure SetValueFormat(const strFormat: string);
    procedure SetDisplayOptional(const blnOptional: boolean);
  end;

  IStorableDefinition = interface
    [cstrGUID_IStorableDefinition]
    function GetAdapterGUID: string;
    function GetDefinitionGUID: string;
  end;

  IStorableDefinitionFriend = interface(IStorableDefinition)
    [cstrGUID_IStorableDefinitionFriend]
    procedure SetAdapterGUID(const strGUID: string);
  end;

  IStorableDefinitionAdapter = interface
    [cstrGUID_IStorableDefinitionAdapter]
    function CreateDefinition: IStorableDefinition;
    function GetAdapterGUID: string;
    procedure Read(const controller: IInterface; const reader: IStructReader; const def: IStorableDefinition);
    procedure Write(const controller: IInterface; const writer: IStructWriter; const def:
      IStorableDefinition);
  end;

  IStorableDefinitionFactory = interface
    [cstrGUID_IStorableDefinitionFactory]
    function CreateDefinition(const strDefinitionGUID: string): IStorableDefinition; overload;
    function GetWriter(const strDefinitionGUID: string): IStorableDefinitionAdapter; overload;
    procedure RegisterAdapter(const strGUID: string; const adapterClass: TInterfacedClass; const defClass:
      TInterfacedClass);
  end;

  IAdapterFactory = interface
    [cstrGUID_IAdapterFactory]
    function FindAdapter(const strGUID: string): boolean; overload;
    function FindAdapter(const intGUID: integer): boolean; overload;
    function CreateAdapter(const strGUID: string): IInterface; overload;
    function CreateAdapter(const intGUID: integer): IInterface; overload;
    procedure RegisterAdapter(const strGUID: string; const adapterClass: TInterfacedClass);
  end;

  IStructReader = interface
    [cstrGUID_IStructReader]
    function FindExternalReference(const strKind: string; const strName: string; out intReference: integer):
      boolean;
    function FindSection(const strName: string; out section: IStructReader): boolean;
    function GetAttributeAsBoolean(const strName: string; const blnDefault: boolean): boolean;
    function GetAttributeAsDate(const strName: string; const realDefault: TDateTime): TDateTime;
    function GetAttributeAsFloat(const strName: string; const realDefault: double): double;
    function GetAttributeAsInteger(const strName: string; const intDefault: integer): integer;
    function GetAttributeAsString(const strName: string; const strDefault: string): string;
    function GetAttributeAsVariant(const strName: string; const varDefault: variant): variant;
    function GetAttributeByIndex(const intIndex: integer; out strName: string): string;
    function HasAttributeValue(const strName: string): boolean;
    function GetLastAttributeIndex: integer;
    function GetLastSectionIndex: integer;
    function GetName: string;
    function GetSectionByIndex(const intIndex: integer): IStructReader;
    function GetSectionText: string;
  end;

  IStructWriter = interface
    [cstrGUID_IStructWriter]
    function CreateSection(const strName: string): IStructWriter;
    function AppendSection(const strName: string; const varValue: Variant): IStructWriter;
    procedure SetAttributeBoolean(const strName: string; const blnValue: boolean);
    procedure SetAttributeDate(const strName: string; const realValue: TDateTime);
    procedure SetAttributeDateTime(const strName: string; const realValue: TDateTime);
    procedure SetAttributeFloat(const strName: string; const realValue: double);
    procedure SetAttributeInteger(const strName: string; const intValue: integer);
    procedure SetAttributeString(const strName: string; const strValue: string);
    procedure SetAttributeVariant(const strName: string; const varValue: Variant);
    procedure SetExternalReference(const strKind: string; const strName: string; const intReference: integer);
    procedure SetSectionText(const strText: string);
    procedure SetSectionBoolean(const blnValue: boolean);
    procedure SetSectionDate(const realValue: TDateTime);
    procedure SetSectionDateTime(const realValue: TDateTime);
    procedure SetSectionFloat(const realValue: double);
    procedure SetSectionInteger(const intValue: integer);
    procedure SetSectionString(const strValue: string);
    procedure SetSectionVariant(const varValue: Variant);
  end;

  IStructDocument = interface
    [cstrGUID_IStructDocument]
    function Read: IStructReader;
    function Write: IStructWriter;
    procedure Release;
  end;

  IStructDocumentPlain = interface(IStructDocument)
    [cstrGUID_IStructDocumentPlain]
  end;

  IStructDocumentFriend = interface(IStructDocument)
    [cstrGUID_IStructDocumentFriend]
    function GetStorageKind: string;
    function ResolveExternalReferenceIn(const strKind: string; const varValue: Variant): integer;
    function ResolveExternalReferenceOut(const strKind: string; const intReference: integer): Variant;
  end;

  IClonable = interface
    [cstrGUID_IClonable]
    function Clone: IClonable;
    function Equals(const other: IInterface): boolean;
    procedure Assign(const other: IInterface);
  end;

  IPrototypeFactory = interface
    [cstrGUID_IPrototypeFactory]
    function GetInstance(const strIdentifier: string): IClonable;
    procedure RegisterPrototype(const strIdentifier: string; const prototype: IClonable);
  end;

  IUserInteraction = interface
    [cstrGUID_IUserInteraction]
    function QueryDecision(const strQuestion: string; out enumResult: TUserConfirmation): boolean;
    function QueryOption(const strQuestion: string; const codes, labels: IStringList; out strResultCode:
      string): boolean;
    procedure ShowMessage(const strMessage: string);
  end;

  IUser = interface
    [cstrGUID_IUser]
    function GetLoginName: string;
  end;

  IUserFriend = interface(IUser)
    [cstrGUID_IUserFriend]
    procedure SetLoginName(const strName: string);
  end;

  ISession = interface
    [cstrGUID_ISession]
    function GetUser: IUser;
    function Interact: IUserInteraction;
    function IsOpen: boolean;
    procedure Close;
  end;

  ISessionFriend = interface
    [cstrGUID_ISessionFriend]
    function GetApplication: IApplicationFriend;
    procedure Initialize(const app: IApplicationFriend; const user: IUser);
  end;

  IApplication = interface
    [cstrGUID_IApplication]
    function Closing: boolean;
    function GetLastSessionIndex: integer;
    function GetSessionByIndex(const intIndex: integer): ISession;
    function OpenSession: ISession;
    procedure Close;
    procedure CloseSession(const session: ISession);
    procedure Start;
    procedure Stop;
  end;

  IApplicationFriend = interface(IApplication)
    [cstrGUID_IApplicationFriend]
    function Interact: IUserInteraction;
    function GetPrototypeFactory: IPrototypeFactory;
  end;

  IIndexedString = interface
    [cstrGUID_IIndexedString]
    function GetIndex: integer;
    function GetValue: string;
    procedure SetIndex(const intIndex: integer);
    procedure SetValue(const strValue: string);
  end;

  IGenericBusinessObject = interface
    [cstrGUID_IGenericBusinessObject]
    function AttributeIsValid(const strIdentifier: string; const Value: Variant): boolean;
    function FindAttribute(const strIdentifier: string; out intIndex: integer): boolean;
    function GetAttribute(const intIndex: integer): Variant; overload;
    function GetAttribute(const strIdentifier: string): Variant; overload;
    function GetAttributeEnumeration(const intIndex: integer): IEnumeration;
    function GetAttributeType(const intIndex: integer): TAttributeType;
    function HasAttribute(const strIdentifier: string): boolean;
    procedure CreateAttribute(const strIdentifier: string; const enumType: TAttributeType; const varValue:
      Variant; const EnumValues: IEnumeration = nil);
    procedure SetAttribute(const intIndex: integer; const varValue: Variant); overload;
    procedure SetAttribute(const strIdentifier: string; const varValue: Variant); overload;
  end;

  IFileSelection = interface
    [cstrGUID_IFileSelection]
    function GetConfirmation: boolean;
    function GetDirectory: string;
    function GetExtensions: string;
    function GetFileMustExist: boolean;
    function GetFiles: IStringList;
    function GetTitle: string;
    procedure SetConfirmation(const blnValue: boolean);
    procedure SetDirectory(const strDir: string);
    procedure SetExtensions(const strExtensions: string);
    procedure SetFileMustExist(const blnValue: boolean);
    procedure SetTitle(const strTitle: string);
  end;

  IGenericOption = interface
    [cstrGUID_IGenericOption]
    function GetHint: string;
    function GetCaption: string;
    function GetCode: string;
    function GetResourceName: string;
  end;

  ILibraryFunction = interface
    [cstrGUID_ILibraryFunction]
    function GetAddress: cardinal;
    function GetName: string;
    procedure SetAddress(const intAddress: cardinal);
  end;

  ILibrary = interface
    [cstrGUID_ILibrary]
    function CreateFunction(const strName: string): ILibraryFunction;
    function FindFunction(const strName: string; out func: ILibraryFunction): boolean;
    function GetFunctionByIndex(const intIndex: integer): ILibraryFunction;
    function GetHandle: cardinal;
    function GetIdentifier: string;
    function GetLastFunctionIndex: integer;
    procedure SetHandle(const intHandle: cardinal);
  end;

  IMemoText = interface
    [cstrGUID_IMemoText]
    function FindLine(const intPos: integer): integer;
    function GetContentHeight: double;
    function GetContentWidth: double;
    procedure SetContentWidth(const realWidth: double);
    function GetLastLineIndex: integer;
    function GetLine(const strText: string; const intLineIndex: integer): string;
    function GetLineBegin(const intLineIndex: integer): integer;
    function IsSameText(const strText: string): boolean;
    function IsSoftWrappedLine(const intLineIndex: integer): boolean;
    procedure AddLine(const intStart, intEnd: integer);
    procedure Initialize(const strText: string);
    procedure SetContentHeight(const realHeight: double);
    procedure Clear;
    procedure TruncateAtLine(const intLineIndex: integer);
  end;

  IPayloadPath = interface
    [cstrGUID_IPayloadPath]
    function GetLastPayloadIndex: integer;
    function GetLeaf: IInterface;
    function GetLeafAs(const intGUID: TGUID; out leaf): boolean;
    function GetPayloadByIndex(const intIndex: integer): IInterface;
    function GetPayloadByIndexAs(const intIndex: integer; const intGUID: TGUID; out leaf): boolean;
    function GetPayloadCount: integer;
    procedure Add(const payload: IInterface);
    procedure Clear;
    function PopLeaf: IInterface;
    procedure PushLeaf(const leaf: IInterface);
    function Clone: IPayloadPath;
    procedure Assign(const value: IInterface);
  end;

  IPayloadNode = interface
    [cstrGUID_IPayloadNode]
    function CreateChild(const payload: IInterface): IPayloadNode;
    function GetChildByIndex(const intIndex: integer): IPayloadNode;
    function GetLastChildIndex: integer;
    function GetPayload: IInterface;
    procedure SetPayload(const payload: IInterface);
    function GetChildCount: integer;
  end;

  IReport = interface
    [cstrGUID_IReport]
    function CreateError: IError; overload;
    function CreateError(const strMessage: string; const source: IInterface): IError; overload;
    function CreateError(const strMessage: string; const exptInfo: Exception; const source: IInterface): IError; overload;
    function CreateInfo: IInfo; overload;
    function CreateInfo(const strMessage: string; const source: IInterface): IInfo; overload;
    function CreateWarning: IWarning; overload;
    function CreateWarning(const strMessage: string; const source: IInterface): IWarning; overload;
    function GetDetailByIndex(const intIndex: integer): IReport;
    function GetDetailByIndexAndInterface(const intIndex: integer; const IID: TGUID; out AInterface): boolean;
    function GetDetailCount: integer;
    function GetLastDetailIndex: integer;
    function GetMessage: string;
    function GetRepresentation: string;
    function GetSource: IInterface;
    function GetTypeAsString: string;
    procedure AddReport(const other: IReport);
    procedure Clear;
    procedure SetMessage(const strText: string);
    procedure SetSource(const source: IInterface);
    procedure SetException(const exptInfo: Exception);
    procedure SetErrorDetails(const strErrorDetails: string);
    function GetText: string;
    function HasErrors: boolean;
    function GetErrorText: string;
    function GetErrorDetails: string;
  end;

  IReportSummary = interface(IReport)
    [cstrGUID_IReportSummary]
    function GetCaption: string;
    function GetDisplaySource: boolean;
    procedure SetCaption(const strCaption: string);
    procedure SetDisplaySource(const blnDisplay: boolean);
  end;

  IReportFactory = interface
    [cstrGUID_IReportFactory]
    function CreateError: IError;
    function CreateInfo: IInfo;
    function CreateWarning: IWarning;
    function CreateSummary(const strCaption, strDescription: string): IReportSummary;
  end;

  IError = interface(IReport)
    [cstrGUID_IError]
  end;

  IInfo = interface(IReport)
    [cstrGUID_IInfo]
  end;

  IWarning = interface(IReport)
    [cstrGUID_IWarning]
  end;

  IImagePalette = interface
    [cstrGUID_IImagePalette]
    function GetColorByIndex(const intIndex: integer): TRGBColor;
    function GetIndexByColor(const intColor: TRGBColor): integer;
    function GetLastIndex: integer;
    procedure SetColorByIndex(const intIndex: integer; const intColor: TRGBColor);
    procedure SetPaletteSize(const intCount: integer);
  end;

  IImageLayer = interface
    [cstrGUID_IImageLayer]
    function GetPixelAt(const intX, intY: integer): TRGBColor;
    procedure SetPixelAt(const intX, intY: integer; const intColor: TRGBColor);
    function GetHash: string;
    procedure FloodAlpha(const intValue: integer);
    procedure SetAlphaAt(const intX, intY: integer; const intAlpha: byte);
  end;

  IImageLayerIndexed = interface(IImageLayer)
    [cstrGUID_IImageLayerIndexed]
    function GetPalette: IImagePalette;
    function GetPaletteIndexAt(const intX, intY: integer): integer;
    procedure SetPaletteIndexAt(const intX, intY: integer; const intIndex: integer);
  end;

  IImageLayerFriend = interface
    [cstrGUID_IImageLayerFriend]
    procedure ApplySize(const size: TIntegerPoint);
  end;

  IImage = interface
    [cstrGUID_IImage]
    function CreateIndexedLayer: IImageLayerIndexed;
    function CreateLayer: IImageLayer;
    function GetColorDepth: TColorDepth;
    function GetDimensions: TFloatPoint;
    function GetHash: string;
    function GetLastLayerIndex: integer;
    function GetLayerByIndex(const intIndex: integer): IImageLayer;
    function GetResolution: double;
    function GetSize: TIntegerPoint;
    procedure SetColorDepth(const enumDepth: TColorDepth);
    procedure SetResolution(const realResolution: double);
    procedure SetSize(const size: TIntegerPoint);
    procedure ConvertColorDepth(const enumDepth: TColorDepth);
  end;

  IImageAdapter = interface
    [cstrGUID_IImageAdapter]
    function GetFormat: TImageFormat;
    function LoadFromFile(const strFilename: string): IImage;
    function LoadFromStream(const reader: IStreamReader): IImage;
    procedure SaveToFile(const image: IImage; const strFilename: string);
    procedure SaveToStream(const image: IImage; const writer: IStreamWriter);
  end;

  IImageAdapterFactory = interface
    [cstrGUID_IImageAdapterFactory]
    function GetAdapter(const enumFormat: TImageFormat): IImageAdapter; overload;
    function GetAdapter(const strFormatExtension: string): IImageAdapter; overload;
  end;

  IImageAcquisition = interface
    [cstrGUID_IImageAcquisition]
    function AcquireImage(const intContextHandle: cardinal; const intColorDepth: integer; const
      blnInteractive: boolean): IImage;
    procedure SelectDevice(const intContextHandle: cardinal);
  end;

  IImageAcquisitionContext = interface
    [cstrGUID_IImageAcquisitionContext]
    function GetAgent: IImageAcquisition;
    function GetImage: IImage;
    procedure SetAgent(const agent: IImageAcquisition);
    procedure SetImage(const image: IImage);
  end;

  ITextQuery = interface
    [cstrGUID_ITextQuery]
    function GetCaption: string;
    function GetPrompt: string;
    function GetValue: string;
    procedure SetCaption(const strCaption: string);
    procedure SetPrompt(const strPrompt: string);
    procedure SetValue(const strValue: string);
  end;

  IProgress = interface
    [cstrGUID_IProgress]
    function GetCurrent: Integer;
    function GetMax: Integer;
    function GetDescription: string;
    function GetMin: Integer;
    function GetStep: Integer;
    function GetUID: Integer;
    procedure SetCurrent(intCurrent: Integer);
    procedure SetMax(intMax: Integer);
    procedure SetDescription(const strDescription: string);
    procedure SetMin(intMin: Integer);
    procedure SetStep(intStep: Integer);
    procedure SetUID(intUID: Integer);
  end;

  IPath = interface
    [cstrGUID_IPath]
    function IsAbsolute: boolean;
    function GetCount: integer;
    function GetFirst: IInterface;
    function GetLeaf: IInterface;
    function GetNext(const element: IInterface): IInterface;
    function GetPrev(const element: IInterface): IInterface;
    function GetText: string;
    procedure SetText(const strPath: string);
    procedure AppendPath(const element: IInterface);
    procedure Clear();
  end;

  IRenderContext = interface
    [cstrGUID_IcaRenderContext]
    function GetHostHandle: cardinal;
    function GetResolution: double;
    function GetShadowPass: boolean;
    procedure SetHostHandle(const intHost: cardinal);
    procedure SetResolution(const realValue: double);
    procedure SetShadowPass(const blnValue: boolean);
    function AlterOpacity(const intColor: TRGBColor; const realFactor: double): TRGBColor;
    function CanBlend: boolean;
    function CentimetersToDevice(const PointCentimeters: TFloatPoint): TIntegerPoint; overload;
    function CentimetersToDevice(const realCentimeters: double): integer; overload;
    function CentimetersToDevice(const rectCM: TFloatRect): TIntegerRect; overload;
    function DeviceToCentimeters(const intDevicePoints: integer): double; overload;
    function DeviceToCentimeters(const PointDevice: TIntegerPoint): TFloatPoint; overload;
    function DeviceToCentimeters(const rectDevice: TIntegerRect): TFloatRect; overload;
    function GetBrushColor: TRGBColor;
    function GetIdentifier: string;
    function GetNativeHandle: cardinal;
    function GetPrintMode: boolean;
    function GetStackDepth: integer;
    function IsLowResolution: boolean;
    procedure ClearClippingRect;
    procedure Pop;
    procedure Push;
    procedure SetBrushColor(const value: TRGBColor);
    procedure SetBrushStyle(const value: TBedrockBrushStyle);
    procedure SetFontName(const value: string);
    procedure SetFontSizeMetric(const value: double);
    procedure SetFontSizePoints(const value: integer);
    procedure SetFontStyle(const enumStyles: TBedrockFontStyles);
    procedure SetInterpolationMode(const enumMode: TBedrockInterpolationMode);
    procedure SetPaintMode(const value: TBedrockPaintMode);
    procedure SetPenColor(const value: TRGBColor);
    procedure SetPenStyle(const value: TBedrockPenStyle);
    procedure SetPrintMode(const blnEnable: boolean);
  end;

  IRenderContextDevice = interface(IRenderContext)
    [cstrGUID_IcaRenderContextDevice]
    function Get130um: integer;
    function Get180um: integer;
    function Get250um: integer;
    function Get350um: integer;
    function Get500um: integer;
    function Get700um: integer;
    procedure BitBlit(const source: TIntegerPlacement; const destination: TIntegerPoint); overload;
    procedure DrawArc(const rectangle: TIntegerRect; const ptStart, ptEnd: TIntegerPoint);
    procedure DrawCircle(const ptCenter: TIntegerPoint; const intRadius: integer);
    procedure DrawEllipse(const rectangle: TIntegerRect);
    procedure DrawLine(const intX1, intY1, intX2, intY2: integer); overload;
    procedure DrawLine(const ptStart, ptEnd: TIntegerPoint); overload;
    procedure DrawLineTo(const pt: TIntegerPoint);
    procedure DrawMoveTo(const pt: TIntegerPoint);
    procedure DrawPie(const rectangle: TIntegerRect; const ptStart, ptEnd: TIntegerPoint);
    procedure DrawPixel(const intX, intY: integer);
    procedure DrawPolygon(Points: array of TIntegerPoint);
    procedure DrawPolyLine(Points: array of TIntegerPoint);
    procedure DrawRectangle(const intX1, intY1, intX2, intY2: integer); overload;
    procedure DrawRectangle(const rectangle: TIntegerRect); overload;
    procedure DrawSubstring(const ptStart: TIntegerPoint; const strText: string; const intStartIndex,
      intEndIndex: integer);
    procedure DrawText(const ptStart: TIntegerPoint; const strText: string); overload;
    procedure MeasureSubstring(const strText: string; const intStartIndex, intEndIndex: integer; out extent:
      TIntegerPoint);
    procedure MeasureTextExtent(const strText: string; out extent: TIntegerPoint); overload;
    procedure SetClippingRect(const rectangle: TIntegerRect);
    procedure SetPenWidth(const intWidth: integer);
    function GetTextAscent: integer;
    function GetTextDescent: integer;
    function FitText(const strLine: string; const intWidth: integer; const intStart, intEnd: integer; out
      intEffectiveWidth: integer): integer;
  end;

  IPage = interface
    [cstrGUID_IPage]
    function GetClientArea: TIntegerRect;
    function GetNumber: integer;
    function GetPrintableArea: TIntegerRect;
    procedure SetClientArea(const rectClient: TIntegerRect);
    procedure SetNumber(const intPageNumber: integer);
    procedure SetPrintableArea(const rectPrintable: TIntegerRect);
    function GetData: IInterface;
    procedure SetData(const data: IInterface);
  end;

  IPrintEngine = interface
    [cstrGUID_IPrintEngine]
    function NewPage: IPage;
    function GetContext: IRenderContext;
    function BeginDocument: IPage;
    procedure EndDocument;
    function GetTotalPageCount: integer;
  end;

  IPageBuilder = interface
    [cstrGUID_IPageBuilder]
    procedure PreparePage(const engine: IPrintEngine; const page: IPage);
    procedure FinalizePage(const engine: IPrintEngine; const page: IPage);
  end;

  IPageDefinition = interface
    [cstrGUID_IPageDefinition]
    function GetResourceNames: IStringList;
    function GetText: string;
    procedure SetText(const strText: string);
  end;

  ITimeProvider = interface
    [cstrGUID_ITimeProvider]
    function GetTime: TDateTime;
  end;

  IClassbasedObjectFactory = interface
    function CreateObjectByIdentifier(const strIdentifier: string): IInterface;
    procedure RegisterClass(const strIdentifier: string; const objectClass: TClass);
  end;

  ITraceFilter = interface
    [cstrGUID_ITraceFilter]
    function decideTraceMessage(const strMessage: string; out strInstead: string): boolean;
    function getTraceFilterStats: IIntegerStringList;

    function getActive: boolean;
    procedure setActive(const active: boolean);

    function getUseDynamicFilter: boolean;
    procedure setUseDynamicFilter(const useDynamic: boolean);

    function getDynamicFilterSize: integer;
    procedure setDynamicFilterSize(const filterSize: integer);

    function getWildCardFilterList: IStringList;
    procedure setWildCardFilterList(const filterList: IStringList);

    function getFilterThreshold: integer;
    procedure setFilterThreshold(const threshold: integer);
  end;

implementation

end.
