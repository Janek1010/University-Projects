function [value] = compute_impedance( omega )
  value = (1/(sqrt((1/725^2) + ((omega*8*10^-5) - 1/(omega*2))^2))) - 75;
end

