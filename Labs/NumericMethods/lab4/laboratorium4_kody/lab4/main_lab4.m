clc
clear all
close all

% A:

[xvect, xdif, fx, it_cnt] = bisection(1,60000,1e-3,@czas);

plot(1:it_cnt,xvect)
title("A - metoda bisekcji przyblizony");
ylabel("przyblizony wynik");
xlabel("numer iteracji");
saveas(gcf, 'A_przyblizenia_bisekcja.png');

semilogy(xdif)
title("A - metoda bisekcji roznica");
ylabel("zmiany wartości przybliżonego rozwiązania dla kolejnych iteracji");
xlabel("numer iteracji");
saveas(gcf, 'A_roznice_bisekcja.png');

[xvect,xdif,fx,it_cnt] = secant(@czas,1,60000,1e-3)

plot(1:it_cnt,xvect)
title("A - metoda siecznych przyblizony");
ylabel("przyblizony wynik");
xlabel("numer iteracji");
saveas(gcf, 'A_przyblizenia_siecznych.png');

semilogy(xdif)
title("A - metoda seicznych roznice");
ylabel("zmiany wartości przybliżonego rozwiązania dla kolejnych iteracji");
xlabel("numer iteracji");
saveas(gcf, 'A_roznice_siecznych.png');

% B:

[xvect, xdif, fx, it_cnt] = bisection(0,50,1e-12,@compute_impedance);

plot(1:it_cnt,xvect)
title("B - metoda bisekcji przyblizony");
ylabel("przyblizony wynik");
xlabel("numer iteracji");
saveas(gcf, 'B_przyblizenia_bisekcja.png');

semilogy(xdif)
title("B - metoda bisekcji roznica");
ylabel("zmiany wartości przybliżonego rozwiązania dla kolejnych iteracji");
xlabel("numer iteracji");
saveas(gcf, 'B_roznice_bisekcja.png');

[xvect,xdif,fx,it_cnt] = secant(@compute_impedance,0,50,1e-12)

plot(1:it_cnt,xvect)
title("B - metoda siecznych przyblizony");
ylabel("przyblizony wynik");
xlabel("numer iteracji");
saveas(gcf, 'B_przyblizenia_sieczne.png');

semilogy(xdif)
title("B - metoda seicznych roznica");
ylabel("zmiany wartości przybliżonego rozwiązania dla kolejnych iteracji");
xlabel("numer iteracji");
saveas(gcf, 'B_roznice_siecznych.png');

% C:

[xvect, xdif, fx, it_cnt] = bisection(0,50,1e-12,@predkosc);

plot(1:it_cnt,xvect)
title("C - metoda bisekcji przblizony");
ylabel("przyblizony wynik");
xlabel("numer iteracji");
saveas(gcf, 'C_przyblizenia_bisekcja.png');

semilogy(xdif)
title("C - metoda bisekcji roznica");
ylabel("zmiany wartości przybliżonego rozwiązania dla kolejnych iteracji");
xlabel("numer iteracji");
saveas(gcf, 'C_roznice_bisekcja.png');

[xvect,xdif,fx,it_cnt] = secant(@predkosc,0,50,1e-12)

plot(1:it_cnt,xvect)
title("C - metoda siecznych przblizony");
ylabel("przyblizony wynik");
xlabel("numer iteracji");
saveas(gcf, 'C_przyblizenia_sieczne.png');

semilogy(xdif)
title("C - metoda seicznych roznica");
ylabel("zmiany wartości przybliżonego rozwiązania dla kolejnych iteracji");
xlabel("numer iteracji");
saveas(gcf, 'C_roznice_siecznych.png');

options = optimset('Display','iter')
fzero(@tan,6,options)
fzero(@tan,4.5,options)

