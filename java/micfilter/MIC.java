package micfilter;

public class MIC {
	public static String[] CurrentProjectNames;
	public static String[] CurrentFilterTypes;
	public static String[] CurrentProjectPaths;
	public static int[] CurrentFilterIDs;
	public static String[][] CurrentFilterInputs;
	public static int nextProjectIndex;
	public static int maxProjects;
	public static final String[] ProjectExts={"*.mic"};
	public static final String ProjectPath="C:\\Users\\Abhishek\\workspace\\FilterViewer\\projects";
	public static final String[] FILTERTYPES={
		"Stepped Impedance Lowpass Chebyshev",
		"Stepped Impedance Lowpass Butterworth",
		"Interdigital Bandpass Filter"
	};
	public static final String[] FILTERDESCRIPTION={
		"This is a Stepped Impedance Lowpass Chebyshev Filter",
		"This is a Stepped Impedance Lowpass Butterworth Filter",
		"This is an Interdigital Band Pass Filter"
	};
	public static final String[][] FILTERSPECS={
		{"Wc","Ws","Am","A","Zo","Zl","Zh","Er","b","type"},
		{"Wc","Ws","A","Zo","Zl","Zh","Er","b","type"},
		{"Wo","Ws","A","fbw","Er","t","b"}
	};
	public static final String[][] FILTERSPECSTEXT={
		{
			"Cutoff Frequency (Hz)",
			"Frequency at which Attenuation is specified (Hz)",
			"Ripple Level (dB)",
			"Specified Attenuation (dB)",
			"Characteristic Impedance of stripline connected to load and generator (ohm)",
			"Characteristic Impedance of stripline for inductor (ohm)",
			"Characteristic Impedance of stripline for capacitor (ohm)",
			"Dielectric Constant",
			"Substrate Width (m)",
			"Type (1:Stripline,2:Microstripline)"
		},
		{	
			"Cutoff Frequency (Hz)",
			"Frequency at which Attenuation is specified (Hz)",
			"Specified Attenuation (dB)",
			"Characteristic Impedance of stripline connected to load and generator (ohm)",
			"Characteristic Impedance of stripline for inductor (ohm)",
			"Characteristic Impedance of stripline for capacitor (ohm)",
			"Dielectric Constant",
			"Substrate Width (m)",
			"Type (1:Stripline,2:Microstripline)"
		},
		{
			"Cutoff Frequency (Hz)",
			"Frequency at which Attenuation is specified (Hz)",
			"Specified Attenuation (dB)",
			"Fractional Bandwidth",
			"Dielectric Constant",
			"Thickness (inches)",
			"Substrate Width (inches)"
		}
	};
	public static final String[] FILTERFUNCTION={
	"stepped_imp_lowpass_Tchebycheff",
	"stepped_imp_lowpass_maxflat",
	"interdigital_maxflat"
	};
	public static final String DEFAULTPATH="C:\\Users\\Abhishek\\workspace\\FilterViewer\\projects\\";
	
	
}
