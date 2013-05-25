{*
 *}
unit SampleUnit2;

interface

uses
  SysUtils,
  Types;

const
  // *** Various constants ***
  //5 minutes corresponds to a bit.
  crealTick: double = 1 / 288;

  // An hour is 1/24 of a day.
  crealYear: double = 365.25;
  crealMonth: double = 30.5;
  crealWeek: double = 7;
  crealDay: double = 1;
  crealHour: double = 1 / 24;
  crealMinute: double = 1 / 1440;
  crealSecond: double = 1 / 86400;
  crealMilliSecond: double = 1 / 86400000;
  creal5Minute: double = 1 / 288;

  cMinPerDay: integer = 1440;
  cMinPerWeek: integer = 1440 * 7;
  cMinPerMonth: integer = 1440 * 30;
  cMinPerYear: integer = 1440 * 365;

  cSecPerHour: integer = 3600;
  cSecPerDay: integer = 86400;

  crealDaysPerMonth: double = ((365 * 4) + 1) / (12 * 4); //=30.4375
  crealDaysPerYear: double = ((365 * 4) + 1) / 4; //=365.25

  // *** Type handling constants ***
  crealMinDouble: double = 5E-324;
  crealMaxDouble: double = 1.7E308;
  cMinInt: integer = -2147483648;
  cMaxInt: integer = 2147483647;


  {* 1 Inch = 2.54 centimeters. *}
  gcrealInchToCentimeters: double = 2.54;

type

  TCharacterSet = set of char;
  TByteBuffer = array of byte;
  TIntegerArray = array of integer;

  {* Bla.
   * Bli.
   * Blu.
   *}
  TRGBColor = cardinal;

  {*
   * @created 15.12.2004 shrek
   *}
  TColorChannel = (
    cchRed,
    cchGreen,
    cchBlue,
    cchAlpha
    );

  TFillChar = (
    fcSpace,
    fcZero,
    fcSpaceLeft
    );

  {* Progress notification event type.
   *}
  TNotifyProgressEvent = procedure(sender: TObject; intDoneSteps: integer; intTotalSteps: integer; const
    strInfo: string) of object;

  {*
   * Analog to TNotifyEvent, but sender is an IUnknown.
   * @created 04.11.2003 shrek
   *}
  TInterfaceNotifyEvent = procedure(const sender: IUnknown) of object;

  TDataElementManipulationDirection = (
    mdAny,
    mdPositional,
    mdValue
    );

  TDataElementManipulationHandle = (
    mhNone,
    mhBegin,
    mhEnd,
    mhLow,
    mhHigh,
    mhBody
    );

  TSubdivisionRecord = record
    realAxisPosition: double;
    realMetricPosition: double;
    intLevel: integer;
  end;

  TSeriesLineStyle = (
    lsNone,
    lsSolid,
    lsDash,
    lsDot,
    lsDashDot,
    lsDashDotDot,
    lsIcon
    );

  TMarkKind = (
    mkTick,
    mkIcon
    );

  TTickShape = (
    tsNone,
    tsSquare,
    tsCircle,
    tsTriangle,
    tsTriangleInverted,
    tsCross,
    tsCrossDiagonal,
    tsStar,
    tsDiamond,
    tsDot
    );

  TDataElementType = (
    dtRegular,
    dtRange,
    dtGantt,
    dtQualitative,
    dtText
    );

  TBoundaryFlag = (
    bfIncludeLeading,
    bfIncludeTrailing
    );
  TBoundaryFlags = set of TBoundaryFlag;

  TSAXProcessingOption = (
    poTrim,
    poRecode,
    poIndent
    );
  TSAXProcessingOptions = set of TSAXProcessingOption;

  TXMLEncoding = (
    encLatin1,
    encUTF8
    );

  TColorDepth = (
    cd1bit,
    cd4bit,
    cd8bit,
    cd16bit,
    cd24bit,
    cd32bit
    );
  TColorDepths = set of TColorDepth;

  TImageFormat = (
    ifBMP,
    ifGIF,
    ifICO,
    ifJPG,
    ifPCX,
    ifPNG,
    ifTIF,
    ifWMF,
    ifMCRI,
    ifTGA
    );
  TImageFormats = set of TImageFormat;


const

  gcintBlack = TRGBColor($FF000000);
  gcintMaroon = TRGBColor($FF000080);
  gcintGreen = TRGBColor($FF008000);
  gcintOlive = TRGBColor($FF008080);
  gcintNavy = TRGBColor($FF800000);
  gcintPurple = TRGBColor($FF800080);
  gcintTeal = TRGBColor($FF808000);
  gcintGray = TRGBColor($FF808080);
  gcintSilver = TRGBColor($FFC0C0C0);
  gcintRed = TRGBColor($FF0000FF);
  gcintLime = TRGBColor($FF00FF00);
  gcintYellow = TRGBColor($FF00FFFF);
  gcintBlue = TRGBColor($FFFF0000);
  gcintFuchsia = TRGBColor($FFFF00FF);
  gcintAqua = TRGBColor($FFFFFF00);
  gcintLightGray = TRGBColor($FFC0C0C0);
  gcintDarkGray = TRGBColor($FF808080);
  gcintWhite = TRGBColor($FFFFFFFF);
  gcintMoneyGreen = TRGBColor($FFC0DCC0);
  gcintSkyBlue = TRGBColor($FFF0CAA6);
  gcintCream = TRGBColor($FFF0FBFF);
  gcintMediumGray = TRGBColor($FFA4A0A0);

  gcColors: array[0..21] of TValueMappingRecord = (
    (intValue: integer(gcintBlack); strIdentifier: 'Black'),
    (intValue: integer(gcintMaroon); strIdentifier: 'Maroon'),
    (intValue: integer(gcintGreen); strIdentifier: 'Green'),
    (intValue: integer(gcintOlive); strIdentifier: 'Olive'),
    (intValue: integer(gcintNavy); strIdentifier: 'Navy'),
    (intValue: integer(gcintPurple); strIdentifier: 'Purple'),
    (intValue: integer(gcintTeal); strIdentifier: 'Teal'),
    (intValue: integer(gcintGray); strIdentifier: 'Gray'),
    (intValue: integer(gcintSilver); strIdentifier: 'Silver'),
    (intValue: integer(gcintRed); strIdentifier: 'Red'),
    (intValue: integer(gcintLime); strIdentifier: 'Lime'),
    (intValue: integer(gcintYellow); strIdentifier: 'Yellow'),
    (intValue: integer(gcintBlue); strIdentifier: 'Blue'),
    (intValue: integer(gcintFuchsia); strIdentifier: 'Fuchsia'),
    (intValue: integer(gcintAqua); strIdentifier: 'Aqua'),
    (intValue: integer(gcintLightGray); strIdentifier: 'LightGray'),
    (intValue: integer(gcintDarkGray); strIdentifier: 'DarkGray'),
    (intValue: integer(gcintWhite); strIdentifier: 'White'),
    (intValue: integer(gcintMoneyGreen); strIdentifier: 'MoneyGreen'),
    (intValue: integer(gcintSkyBlue); strIdentifier: 'SkyBlue'),
    (intValue: integer(gcintCream); strIdentifier: 'Cream'),
    (intValue: integer(gcintMediumGray); strIdentifier: 'MedGray')
    );

  gcModesOfDispatch: array[0..4] of TValueMappingRecord = (
    (intValue: Ord(modUnspecified); strIdentifier: 'Unspecified'),
    (intValue: Ord(modLetter); strIdentifier: 'Letter'),
    (intValue: Ord(modFax); strIdentifier: 'Fax'),
    (intValue: Ord(modEMail); strIdentifier: 'E-Mail'),
    (intValue: Ord(modSecuredEMail); strIdentifier: 'Secure E-Mail')
    );

  cstrImageExtensions: array[TImageFormat] of string = (
    'BMP',
    'GIF',
    'ICO',
    'JPG',
    'PCX',
    'PNG',
    'TIF',
    'WMF',
    'MCRI',
    'TGA'
    );

  cstrCheckStates: array[TCheckState] of string = (
    'checked',
    'unchecked',
    'undecided'
  );
  
  cstrDocumentMIMETypes: array[TDocumentFormat] of string = (
    'text/plain',
    'application/rtf',
    'application/msword',
    'application/msword',
    'application/pdf',
    'image/jpeg',
    'image/png',
    'application/msexcel',
    'application/msexcel',
    'application/mspowerpoint',
    'application/mspowerpoint',
    'application/mspowerpoint',
    'application/mspowerpoint'
  );

  //////////////////////////////////////////////////////////////////////////////
  // Language constants
  cstrLanguageCode_enGB = 'en-GB';
  cstrLanguageCode_deCH = 'de-CH';
  cstrLanguageCode_deDE = 'de-DE';
  cstrLanguageCode_frCH = 'fr-CH';
  cstrLanguageCode_huHU = 'hu-HU';
  cstrLanguageCode_itCH = 'it-CH';
  cstrLanguageCode_nlNL = 'nl-NL';
  cstrLanguageCode_csCZ = 'cs-CZ';
  cintLocale_enGB = $809;
  cintLocale_deCH = $807;
  cintLocale_deDE = $407;
  cintLocale_frCH = $100C;
  cintLocale_huHU = $40E;
  cintLocale_itCH = $810;
  cintLocale_nlNL = $413;
  cintLocale_csCZ = $405;
  cstrLanguage_deCH = 'German (Switzerland)';
  cstrLanguage_deDE = 'German (Germany)';
  cstrLanguage_enGB = 'English (GB)';
  cstrLanguage_frCH = 'French (Switzerland)';
  cstrLanguage_huHU = 'Magyar,Hungarian (Hungary)';
  cstrLanguage_itCH = 'Italian (Switzerland)';
  cstrLanguage_nlNL = 'Dutch (Netherlands)';
  cstrLanguage_csCZ = 'Czech (Czech Republic)';
  //////////////////////////////////////////////////////////////////////////////

implementation

end.
