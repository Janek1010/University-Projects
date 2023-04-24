function [roznica] = predkosc(t)
  v = 2000*log(150000/(150000-2700*t)) - 9.81*t;
  roznica =  v - 750;
end