/**
 * 
 * @author Fritz
 */
public class Math {
	//Funktion zum runden
	static public int round(int number, int roundto) {
		if ((number % roundto) >= (roundto / 2)) {
			return number + (roundto - (number % roundto));
		} else {
			return number - (number % roundto);
		}
	}

}
