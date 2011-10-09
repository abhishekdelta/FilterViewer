function [W,L]=stripline(Z,Er,b,Bl,Wc);
%[W,L]=stripline_parameters(Z,Er,b,Bl,Wc);
%W= width of stripline
%L= physical length of the stripline
%Z=characterstic impedance
%Er=dielectric constant
%b=substrate width
%Bl=electric length of stripline
%Wc= cutoff frequency


x=30*pi/(sqrt(Er)*Z)-0.441;

if(sqrt(Er)*Z<120)
    W_b_ratio=x;
else
    W_b_ratio=0.85-sqrt(0.6-x);
end

W=W_b_ratio*b;

c=3*10^8;

L=Bl*c/(sqrt(Er)*Wc);

end