function [W,L,Z,n]=stepped_imp_lowpass_Tchebycheff(Wc,Ws,Am,A,Zo,Zl,Zh,Er,b,type)

%calculates stepped impedance Tchebycheff lowpass filter
%W,L,Z= width, length and characteristic impedance of stripline of various sections
%Wc= cutoff frequency
%Ws= frequency at which attenuation is specified
%Am= ripple level at ww=(W/Wm)
%A= Attenuation at Ws
%Zo= characteristic impedance of stripline connected to load and generator
%Zl,Zh= characteristic impedance of stripline for inductor and capacitor
%Er= dielectric constant
%b= substrate width
%type= 1-'stripline' or 2-'microstripline'


clc;
[n]=order_Tchebycheff(Am,Wc,Ws,A);
n=n;
if(n<=0)
    error('One or more of your inputs is invalid');
end
n

[s,g,r]=Tchebycheff(n,Am);

for(i=1:2:n)
    Bl(i)=g(i)*Zl/Zo;
end

for(i=2:2:n)
    Bl(i)=g(i)*Zo/Zh;
end

We=[];Z=[];L=[];

if(type==1)    %implies stripline design
    
    %calculating impedances
    for(j=1:n)
        if(rem(j,2)==1)
            Z(j)=Zl;
        else
            Z(j)=Zh;
        end
    end
    
    %calculating effective width and length
    for(j=1:n)
        [W(j),L(j)]=stripline(Z(j),Er,b,Bl(j),Wc);
    end
    
    [Wg,Lg]=stripline(Zo,Er,b,pi/2,Wc);
    Zg=Zo;
    Zr=Zo;
    [Wr,Lr]=stripline(Zr,Er,b,pi/2,Wc);
    
elseif(type==2)        %implies microstrip design

    %calculating impedances
    for(j=1:n)
        if(rem(j,2)==1)
            Z(j)=Zl;
        else
            Z(j)=Zh;
        end
    end
    
    %calculating effective width and length
    for(j=1:n)
        [W(j),L(j),Ee(j)]=microstripline(Z(j),Er,b,Bl(j),Wc);
    end
    
    
    [Wg,Lg]=microstripline(Zo,Er,b,pi/2,Wc);
    Zg=Zo;
    Zr=Zo;
    [Wr,Lr]=microstripline(Zr,Er,b,pi/2,Wc);
    
end

W=[Wg W Wr];
L=[Lg L Lr];
Z=[Zg Z Zr];

figure;
[X,Y,Z]=plot_stepped_imp_lpf(W,L);

end
    
    