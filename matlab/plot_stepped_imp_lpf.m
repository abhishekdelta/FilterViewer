function [X,Y,Z]=plot_stepped_imp_lpf(W,L);
W=W*10^3;
L=L*10^3;
n=length(L);
l=[];

for(i=1:n)
    l=[l sum(L(1:i))];
end
X=[0 0];
for(i=1:n-1)
    X=[X;l(i) l(i);l(i) l(i)];
end
X=[X;l(n) l(n)];

Y=[];

for(i=1:n)
Y=[Y;-W(i)/2 W(i)/2;-W(i)/2 W(i)/2];
end

Z=ones(2*n,2);

C=[1 0 0];

surf(X,Y,Z,'EdgeColor',C,'FaceColor',C);
colormap winter;
xlabel('x(mm)');ylabel('y(mm)');zlabel('z(mm)');

end