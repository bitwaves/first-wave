{*
 *}
unit SampleUnit5;

interface

uses
  SampleUnit2,
  bu_ControlAbstractionTypes,
  bu_ControlAbstractionInterfaces,
  u_BPPopupButton,
  u_BedrockTypes,
  u_BedrockInterfaces;

type

  TBPArrowPopupButton = class(
      TBPPopupButton,
      IBPArrowPopupButton)
  protected
    procedure InternalLayout(const context: IBedrockRenderContext; const control: IBedrockControl; var area:
      TIntegerRect; const clip: TIntegerRect; const style: IBedrockStyle; const strPath: string; const payload:
      IInterface; const openEdges: TBooleanRect); override;
    function GetGUID: string; override;
  end;

implementation

uses
  dbu_ToolboxUtils;

{ TBPArrowPopupButton }

function TBPArrowPopupButton.GetGUID: string;
begin
  result:= cstrGUID_IBPArrowPopupButton;
end;

procedure TBPArrowPopupButton.InternalLayout(
  const context: IBedrockRenderContext;
  const control: IBedrockControl;
  var area: TIntegerRect;
  const clip: TIntegerRect;
  const style: IBedrockStyle;
  const strPath: string;
  const payload: IInterface;
  const openEdges: TBooleanRect);
var
  region: TIntegerRect;
  borders: TIntegerRect;
  pebble: IBedrockWidget;
  intDelta: integer;
  strPanelPath: string;
begin
  inherited;
  strPanelPath:= ExtendPath(strPath, 'panel');
  borders:= context.GetDevice.DetermineBorderStrengthRaised(style.GetOrnaments);
  if openEdges.blnLeft or openEdges.blnRight then begin
    intDelta:= (GetStandardIconSize(context) + borders.intLeft + borders.intRight) - (area.intRight -
      area.intLeft);
    if intDelta > 0 then begin
      if openEdges.blnLeft then
        area.intLeft:= area.intLeft - intDelta
      else
        area.intRight:= area.intRight + intDelta;
    end;
  end;
  region:= ShrinkRect(area, borders);
  { Icon auslegen }
  if IsDepressed(control, strPath) then begin
    region:= TranslateRect(region, borders.intLeft div 2, borders.intTop div 2);
  end;
  pebble:= GetPebble(cstrGUID_IBPArrowIcon);
  pebble.Layout(context, control, region, clip, style, ExtendPath(strPanelPath, 'symbol'), payload,
    cFalseRect); // innerRect
  { Panel auslegen }
  pebble:= GetPebble(cstrGUID_IBPButtonPanelIntra);
  pebble.Layout(context, control, area, clip, style, strPanelPath, payload, cFalseRect);
end;

end.
