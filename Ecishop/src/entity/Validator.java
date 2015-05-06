package entity;

public class Validator {
	
	
	private static String[] dominios = {"abogado","ac","academy","accountants","active","actor","ad","adult","ae","aero","af","ag","agency","ai","airforce","al","allfinanz","alsace","am","amsterdam","an","android","ao","aq","aquarelle","ar","archi","army","arpa","as","asia","associates","at","attorney","au","auction","audio","autos","aw","ax","axa","az","ba","band","bank","bar","bargains","bayern","bb","bd","be","beer","berlin","best","bf","bg","bh","bi","bid","bike","bio","biz","bj","black","blackfriday","bloomberg","blue","bm","bmw","bn","bnpparibas","bo","boo","boutique","br","brussels","bs","bt","budapest","build","builders","business","buzz","bv","bw","by","bz","bzh","ca","cab","cal","camera","camp","cancerresearch","capetown","capital","caravan","cards","care","career","careers","cartier","casa","cash","cat","catering","cc","cd","center","ceo","cern","cf","cg","ch","channel","cheap","christmas","chrome","church","ci","citic","city","ck","cl","claims","cleaning","click","clinic","clothing","club","cm","cn","co","coach","codes","coffee","college","cologne","com","community","company","computer","condos","construction","consulting","contractors","cooking","cool","coop","country","cr","credit","creditcard","cricket","crs","cruises","cu","cuisinella","cv","cw","cx","cy","cymru","cz","dad","dance","dating","day","de","deals","degree","delivery","democrat","dental","dentist","desi","dev","diamonds","diet","digital","direct","directory","discount","dj","dk","dm","dnp","do","docs","domains","doosan","durban","dvag","dz","eat","ec","edu","education","ee","eg","email","emerck","energy","engineer","engineering","enterprises","equipment","er","es","esq","estate","et","eu","eurovision","eus","events","everbank","exchange","expert","exposed","fail","farm","fashion","feedback","fi","finance","financial","firmdale","fish","fishing","fit","fitness","fj","fk","flights","florist","flowers","flsmidth","fly","fm","fo","foo","forsale","foundation","fr","frl","frogans","fund","furniture","futbol","ga","gal","gallery","garden","gb","gbiz","gd","ge","gent","gf","gg","ggee","gh","gi","gift","gifts","gives","gl","glass","gle","global","globo","gm","gmail","gmo","gmx","gn","google","gop","gov","gp","gq","gr","graphics","gratis","green","gripe","gs","gt","gu","guide","guitars","guru","gw","gy","hamburg","haus","healthcare","help","here","hiphop","hiv","hk","hm","hn","holdings","holiday","homes","horse","host","hosting","house","how","hr","ht","hu","ibm","id","ie","il","im","immo","immobilien","in","industries","info","ing","ink","institute","insure","int","international","investments","io","iq","ir","irish","is","it","iwc","je","jetzt","jm","jo","jobs","joburg","jp","juegos","kaufen","kddi","ke","kg","kh","ki","kim","kitchen","kiwi","km","kn","koeln","kp","kr","krd","kred","kw","ky","kz","la","lacaixa","land","lat","latrobe","lawyer","lb","lc","lds","lease","legal","lgbt","li","lidl","life","lighting","limited","limo","link","lk","loans","london","lotte","lotto","lr","ls","lt","ltda","lu","luxe","luxury","lv","ly","ma","madrid","maison","management","mango","market","marketing","marriott","mc","md","me","media","meet","melbourne","meme","memorial","menu","mg","mh","miami","mil","mini","mk","ml","mm","mn","mo","mobi","moda","moe","monash","money","mormon","mortgage","moscow","motorcycles","mov","mp","mq","mr","ms","mt","mu","museum","mv","mw","mx","my","mz","na","nagoya","name","navy","nc","ne","net","network","neustar","new","nexus","nf","ng","ngo","nhk","ni","ninja","nl","no","np","nr","nra","nrw","nu","nyc","nz","okinawa","om","one","ong","onl","ooo","org","organic","osaka","otsuka","ovh","pa","paris","partners","parts","party","pe","pf","pg","ph","pharmacy","photo","photography","photos","physio","pics","pictures","pink","pizza","pk","pl","place","plumbing","pm","pn","pohl","poker","porn","post","pr","praxi","press","pro","prod","productions","prof","properties","property","ps","pt","pub","pw","py","qa","qpon","quebec","re","realtor","recipes","red","rehab","reise","reisen","reit","ren","rentals","repair","report","republican","rest","restaurant","reviews","rich","rio","rip","ro","rocks","rodeo","rs","rsvp","ru","ruhr","rw","ryukyu","sa","saarland","sale","samsung","sarl","sb","sc","sca","scb","schmidt","schule","schwarz","science","scot","sd","se","services","sew","sexy","sg","sh","shiksha","shoes","shriram","si","singles","sj","sk","sky","sl","sm","sn","so","social","software","sohu","solar","solutions","soy","space","spiegel","sr","st","su","supplies","supply","support","surf","surgery","suzuki","sv","sx","sy","sydney","systems","sz","taipei","tatar","tattoo","tax","tc","td","technology","tel","tf","tg","th","tienda","tips","tires","tirol","tj","tk","tl","tm","tn","to","today","tokyo","tools","top","town","toys","tp","tr","trade","training","travel","trust","tt","tui","tv","tw","tz","ua","ug","uk","university","uno","uol","us","uy","uz","va","vacations","vc","ve","vegas","ventures","versicherung","vet","vg","vi","viajes","video","villas","vision","vlaanderen","vn","vodka","vote","voting","voto","voyage","vu","wales","wang","watch","webcam","website","wed","wedding","wf","whoswho","wien","wiki","williamhill","wme","work","works","world","ws","wtc","wtf","xxx","xyz","yachts","yandex","ye","yoga","yokohama","youtube","yt","za","zip","zm","zone","zuerich","zw"};
	
	private static char[] char_specials = {'.','!','@','#','$','%','^','&','(',')','{','}','[',']',':',';','<','>',',','.','?','/','~','_','+','-','=','|'};
 	
	public static boolean validateEmail(String email){
		String[] stringv = email.split("@"); //Debe tener una arroba
		boolean retorno = false;
		if(stringv.length==2){//Debe tener usuario y dominio
			if(stringv[0].length()>0 && stringv[1].length()>0){//Tienen que ser no-vacios
				stringv = stringv[1].split("."); //Tiene que tener al mennos un punto
				if(stringv.length>1 && noestanvacios(stringv)){ //Si todos los campos no son vacios y el dominio tiene un formato valido (nombre.extension)
					if(contains(stringv[stringv.length-1])){ //Si el dominio existe en la lista de dominios
						retorno = true; //Es valido
					}
				}
			}
		}
		return retorno;
	}

	private static boolean contains(String string) {
		boolean retorno = false;
		for (int i = 0; i < dominios.length && !retorno; i++) {
	        if (dominios[i].equals(string)) {
	            retorno = true;
	        }
	    }
	    return retorno;
	}

	private static boolean noestanvacios(String[] stringv) {
		boolean retorno = true;
		for (int i = 0; i < stringv.length && retorno; i++) {
	        if (stringv[i].isEmpty()) {
	        	retorno = false;
	        }
	    }
	    return retorno;
	}

	
	public static boolean validatePassword(String pass){
		String [] string = pass.split("");
		String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String[] charac = {"#", "$", "%", "&", "/", "(", ")", "=","?", "¿", "@", ".", "!", "¡", "+", "*", "-", "{", "}"};
		String[] lett = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "ñ", "o","p", "q", 
				"r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		boolean ch = false;
		boolean num = false;
		boolean let = false;
		for (int i = 0; i< string.length && !(ch&&num&&let); i++){
			for (int j = 0; j < charac.length; j++) ch = charac[j].equals(string[i]) || ch;
			for (int j = 0; j < numbers.length; j++) num = numbers[j].equals(string[i]) || num;
			for (int j = 0; j < lett.length; j++) let = lett[j].equals(string[i]) || let;
		}
		return string.length > 7 && ch && num&&let; 
	}
	
	public static boolean validateCardNumber(String s) {
        return LuhnAlgorithm(s, false) % 10 == 0;
    }
	
	private static int LuhnAlgorithm(String s, boolean evenPosition) {
        int sum = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(s.substring(i, i + 1));
            if (evenPosition) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            evenPosition = !evenPosition;
        }

        return sum;
    }

}
