function [n]=order_maxflat(A,Wc,Ws);
%[n]=order_maxflat(A,Wc,Ws);
%Wc= 3 dB level
%Ws= frequency at which attenuation is specified
%A= Attenuation at Ws

n=ceil(0.5*(log((10)^(A/10)-1)/log(Ws/Wc)));

end