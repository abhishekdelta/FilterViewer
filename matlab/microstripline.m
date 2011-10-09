function [W,L,Ee]=microstripline(Z,Er,b,Bl,Wc);
%[W,L]=microstripline_parameters(Z,Er,b,Bl,Wc);
%W= width of stripline
%L= physical length of the stripline
%Z=characterstic impedance
%Er=dielectric constant
%b=substrate width
%Bl=electric length of stripline
%Wc= cutoff frequency

A=(Z/60)*sqrt((Er+1)/2)+((Er-1)/(Er+1))*(0.23+0.11/Er);
B= 377*pi/(2*Z*sqrt(Er));

%considering W_b_ratio to be greater than 2
W_b_ratio=(2/pi)*(B-1-log(2*B-1)+((Er-1)/(2*Er))*(log(B-1)+0.39-0.61/Er));

if(W_b_ratio<2) %if the calculated W_b_ratio is less than 2
    
    W_b_ratio=8*exp(A)/(exp(2*A)-2);
    
end

%calculation of effective dielectric constant
if W_b_ratio>=1
    Ee=(Er+1)/2+(Er-1)/(2*sqrt(1+12*(1/W_b_ratio)));
else
    Ee=(Er+1)/2+(Er-1)/(2*sqrt(1+12*(1/W_b_ratio))+0.04*(1-W_b_ratio)^2);
end

%calculation of effective width
W=W_b_ratio*b;

%calculation of length
c=3*10^8;
L=Bl*c/(sqrt(Ee)*Wc);

end