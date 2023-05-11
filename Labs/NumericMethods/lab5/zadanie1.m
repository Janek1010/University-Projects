[XX,YY]=meshgrid(linspace(0,1,101),linspace(0,1,101));

for i =[ 5, 15, 25, 35]
    sgtitle(i)
    [x,y,f,xp,yp]=lazik(i);

    % 1
    subplot(2,2,1)
    plot(xp,yp,'-o','linewidth',1, 'markersize',1.5)
    title("droga ruchu lazika")
    xlabel("x [km]")
    ylabel("y [km]")

    % 2
    subplot(2,2,2)
    surf(reshape(x,i,i),reshape(y,i,i),reshape(f,i,i))
    shading flat;
    title("wartości pomiarów")
    xlabel("x [km]")
    ylabel("y [km]")
    zlabel("f(x,y)")
    % 3
    subplot(2,2,3)
    [p]=polyfit2d(x,y,f);
    [FP]=polyval2d(XX,YY,p);
    surf(XX,YY,FP)
    shading flat;
    title("interpolacja wielomianowa")
    xlabel("x [km]")
    ylabel("y [km]")
    zlabel("f(x,y)")

    % 4
    subplot(2,2,4)
    [t]=trygfit2d(x,y,f)
    [FT]=trygval2d(XX,YY,t)
    surf(XX,YY,FT)
    shading flat;
    title("interpolacja trygonometryczna")
    xlabel("x [km]")
    ylabel("y [km]")
    zlabel("f(x,y)")

    print (gcf, strcat("K_", num2str(i), ".png"), '-dpng', '-r450')
end
