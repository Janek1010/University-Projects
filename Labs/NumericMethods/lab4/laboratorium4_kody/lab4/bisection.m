function [xvect,xdif,fx,it_cnt] = bisection(a,b,eps,fun)
% fun - funkcja, ktorej miejsce zerowe bedzie poszukiwane
% [a,b] - przedzial poszukiwania miejsca zerowego
% eps - prog dokladnosci obliczen
% 
% xvect - wektor kolejnych wartosci przyblizonego rozwiazania
% xdif - wektor roznic pomiedzy kolejnymi wartosciami przyblizonego rozwiazania
% fx - wektor wartosci funkcji dla kolejnych elementow wektora xvect
% it_cnt - liczba iteracji wykonanych przy poszukiwaniu miejsca zerowego

cprevious = a;
xvect = [];
xdif = [];
fx = [];

for i = 1:3000
    c = (a + b)/2;
    fc = fun(c);    % wartosci funkcji fun dla wartosci c

    xvect(i) = c;
    xdif(i) = abs(cprevious - c);
    fx(i) = fc;
    cprevious = c;

    if abs(fc) < eps || abs(b-a) < eps
        it_cnt = i;
        return;
    elseif fun(a) * fc < 0
        b = c;
    else
        a = c;
    end
end

end

