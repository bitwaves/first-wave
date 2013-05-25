unit Planets;

interface

type

  TMercury = class
  public
  	procedure DoThing;
  end;
  
  TVenus = class
  public
  	procedure DoThing;
  end;
  
  TEarth = class
  public
  	procedure DoThing;
  end;
  
  TMars = class
  public
  	procedure DoThing;
  end;
  
implementation

procedure TMercury.DoThing;
begin
  SomeFunction(cstrGUID_Venus);  
end;

procedure TVenus.DoThing;
begin
  SomeFunction(cstrGUID_Earth);  
  SomeFunction(cstrGUID_Mars);  
end;

procedure TEarth.DoThing;
begin
  SomeFunction(cstrGUID_Mars);  
end;

procedure TMars.DoThing;
begin
  SomeFunction(cstrGUID_Jupiter);  
end;


end.
