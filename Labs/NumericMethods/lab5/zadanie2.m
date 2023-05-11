div_wiel = [];
div_tryg = [];

for i = 5:45
    [XX,YY]=meshgrid(linspace(0,1,101),linspace(0,1,101));
    [x,y,f,xp,yp]=lazik(i);

    [p]=polyfit2d(x,y,f);
    [FP]=polyval2d(XX,YY,p);
    
    [t]=trygfit2d(x,y,f)
    [FT]=trygval2d(XX,YY,t)

    if i ~= 5
        div_wiel(end+1) = max(max(abs(FP - FF_prev)));
        div_tryg(end+1) = max(max(abs(FT - FT_prev)));
    end
        FF_prev = FP;
        FT_prev = FT;
end

plot(6:45, div_wiel);
title("zbieżność Div(K) interpolacji wielomianowej");
ylabel("Div(K)");
xlabel("punkty pomiarowe - K");
print (gcf, strcat("Zbieznosc_interpolacja_wiel.png"), '-dpng', '-r450');

plot(6:45, div_tryg);
title("zbieżność Div(K) interpolacji trygonometrycznej");
ylabel("Div(K)");
xlabel("punkty pomiarowe - K");
print (gcf, strcat("Zbieznosc_interpolacja_tryg.png"), '-dpng', '-r450');