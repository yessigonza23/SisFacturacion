package ec.gob.mdg.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class CedulaRucnew {

	public static String comprobacion(String cadena, String tipo) throws Exception {
		String mensaje = "";
		boolean tipoCadena = false;

		////////
		if (cadena != null && tipo != null) {
			for (int x = 0; x < cadena.length(); x++) {
				char c = cadena.charAt(x);
				// Si no está entre a y z, ni entre A y Z, ni es un espacio
				if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
					tipoCadena = false;
					break;
				} else {
					tipoCadena = true;
				}

			}
		}
		//////////////

		if (cadena != null && tipo != null) {
			if (tipo.equals("P")) {
				mensaje = "T";
				return mensaje;

			} else if (cadena.equals("")) {
				mensaje = "No ha ingresado los caracteres suficientes en la identificación para proceder";
				return mensaje;
			} else if (cadena.length() == 10 && tipo.equals("R")) {
//					System.out.println("1");
				mensaje = "Tipo de identificación no corresponde";
				return mensaje;

			} else if (cadena.length() == 13 && tipo.equals("C")) {
//					System.out.println("2");
				mensaje = "Tipo de identificación no corresponde";
				return mensaje;
			} else if (tipoCadena == false && tipo.equals("R")) {
//					System.out.println("3");
				mensaje = "Tipo de identificación no corresponde";
				return mensaje;
			} else if (tipoCadena == false && tipo.equals("C")) {
//					System.out.println("4");
				mensaje = "Tipo de identificación no corresponde";
				return mensaje;
			} else if (cadena.length() == 10) {

				return validarCedula(cadena, 1);

			} else if (cadena.length() == 13) {
				mensaje = validarRucPersonaNatural(cadena, 2);
				if (mensaje.equals("T")) {
					return mensaje;
				} else {
					mensaje = validarRucSociedadPrivada(cadena, 3);
					return mensaje;
				}
			}
		}

		return "Informacion erronea";
	}

	///////
	public static String validarCedula(String numero, Integer tipo) throws Exception {
		try {
			validarInicial(numero, 10);
			validarCodigoProvincia(numero.substring(0, 2));
			validarTercerDigito(String.valueOf(numero.charAt(2)), tipo);
			algoritmoModulo10(numero, Integer.parseInt(String.valueOf(numero.charAt(9))));
		} catch (Exception e) {
			e.printStackTrace();
			return "Cédula mal ingresada";
		}

		return "T";
	}

	/**
	 * @param numero de ruc persona natural
	 * @return true si es un documento v&aacute;lido
	 * @throws Exception
	 */
	public static String validarRucPersonaNatural(String numero, Integer tipo) throws Exception {
		try {
			validarInicial(numero, 13);
			validarCodigoProvincia(numero.substring(0, 2));
			validarTercerDigito(String.valueOf(numero.charAt(2)), tipo);
			validarCodigoEstablecimiento(numero.substring(10, 13));
			algoritmoModulo10(numero.substring(0, 9), Integer.parseInt(String.valueOf(numero.charAt(9))));
		} catch (Exception e) {
			e.printStackTrace();
			return "Mal ingresado Ruc Persona Natural";
		}

		return "T";
	}

	/**
	 * @param numero ruc empresa privada
	 * @return
	 * @throws Exception
	 */
	public static String validarRucSociedadPrivada(String numero, Integer tipo) throws Exception {

		// validaciones
		try {
			validarInicial(numero, 13);
			validarCodigoProvincia(numero.substring(0, 2));
			validarTercerDigito(String.valueOf(numero.charAt(2)), tipo);
			validarCodigoEstablecimiento(numero.substring(10, 13));
			algoritmoModulo11(numero.substring(0, 9), Integer.parseInt(String.valueOf(numero.charAt(9))), tipo);
		} catch (Exception e) {
			return "Ruc mal ingresado de sociedad privada";
		}

		return "T";
	}

	/**
	 * @param numero
	 * @param caracteres
	 * @return
	 * @throws Exception
	 */
	protected static boolean validarInicial(String numero, int caracteres) throws Exception {
		if (StringUtils.isEmpty(numero)) {
			throw new Exception("Valor no puede estar vacio");
		}

		if (!NumberUtils.isDigits(numero)) {
			throw new Exception("Valor ingresado solo puede tener dígitos");
		}

		if (numero.length() != caracteres) {
			throw new Exception("Valor ingresado debe tener " + caracteres + " caracteres");
		}

		return true;
	}

	/**
	 * @param n&uacutemero en el rango de n&uacutemeros de provincias del ecuador
	 * @return
	 * @throws Exception
	 */
	protected static boolean validarCodigoProvincia(String numero) throws Exception {
		if (Integer.parseInt(numero) < 0 || Integer.parseInt(numero) > 24) {
			throw new Exception("Codigo de Provincia (dos primeros dígitos) no deben ser mayor a 24 ni menores a 0");
		}

		return true;
	}

	/**
	 * @param numero
	 * @param tipo   de documento cedula, ruc
	 * @return
	 * @throws Exception
	 */
	protected static boolean validarTercerDigito(String numero, Integer tipo) throws Exception {
		switch (tipo) {
		case 1:
		case 2:

			if (Integer.parseInt(numero) < 0 || Integer.parseInt(numero) > 5) {
				throw new Exception(
						"Tercer dígito debe ser mayor o igual a 0 y menor a 6 para cédulas y RUC de persona natural ... permitidos de 0 a 5");
			}
			break;
		case 3:
			if (Integer.parseInt(numero) != 9) {
				throw new Exception("Tercer dígito debe ser igual a 9 para sociedades privadas");
			}
			break;

		case 4:
			if (Integer.parseInt(numero) != 6) {
				throw new Exception("Tercer dígito debe ser igual a 6 para sociedades públicas");
			}
			break;
		default:
			throw new Exception("Tipo de Identificacion no existe.");
		}

		return true;
	}

	/**
	 * @param digitosIniciales
	 * @param digitoVerificador
	 * @return
	 * @throws Exception
	 */
	protected static boolean algoritmoModulo10(String digitosIniciales, int digitoVerificador) throws Exception {
		Integer[] arrayCoeficientes = new Integer[] { 2, 1, 2, 1, 2, 1, 2, 1, 2 };

		Integer[] digitosInicialesTMP = new Integer[digitosIniciales.length()];
		int indice = 0;
		for (char valorPosicion : digitosIniciales.toCharArray()) {
			digitosInicialesTMP[indice] = NumberUtils.createInteger(String.valueOf(valorPosicion));
			indice++;
		}

		int total = 0;
		int key = 0;

		for (Integer valorPosicion : digitosInicialesTMP) {
			if (key < arrayCoeficientes.length) {
				valorPosicion = (digitosInicialesTMP[key] * arrayCoeficientes[key]);

				if (valorPosicion >= 10) {
					char[] valorPosicionSplit = String.valueOf(valorPosicion).toCharArray();
					valorPosicion = (Integer.parseInt(String.valueOf(valorPosicionSplit[0])))
							+ (Integer.parseInt(String.valueOf(valorPosicionSplit[1])));

				}
				total = total + valorPosicion;
			}

			key++;
		}
		int residuo = total % 10;
		int resultado;

		if (residuo == 0) {
			resultado = 0;
		} else {
			resultado = 10 - residuo;
		}

		if (resultado != digitoVerificador) {
			throw new Exception("Dígitos iniciales no validan contra Dígito Idenficador");
		}

		return true;
	}

	/**
	 * @param numero
	 * @return
	 * @throws Exception
	 */
	protected static boolean validarCodigoEstablecimiento(String numero) throws Exception {
		if (Integer.parseInt(numero) < 1) {
			throw new Exception("Código de establecimiento no puede ser 0");
		}
		return true;
	}

	/**
	 * @param digitosIniciales
	 * @param digitoVerificador
	 * @param tipo
	 * @return
	 * @throws Exception
	 */
	protected static boolean algoritmoModulo11(String digitosIniciales, int digitoVerificador, Integer tipo)
			throws Exception {
		List<Integer> arrayCoeficientes = null;

		switch (tipo) {

		case 3:
			arrayCoeficientes = Arrays.asList(4, 3, 2, 7, 6, 5, 4, 3, 2);
			break;
		case 4:
			arrayCoeficientes = Arrays.asList(3, 2, 7, 6, 5, 4, 3, 2);
			break;
		default:
			throw new Exception("Tipo de Identificacion no existe.");
		}

		List<Integer> digitosInicialesTMP = IntStream.range(0, digitosIniciales.length())
				.mapToObj(i -> NumberUtils.createInteger(String.valueOf(digitosIniciales.charAt(i))))
				.collect(Collectors.toCollection(() -> new ArrayList<>(digitosIniciales.length())));

		AtomicInteger consolidadodMultiplicacionIndiceConeficiente = new AtomicInteger();
		List<Integer> finalArrayCoeficientes = arrayCoeficientes;
		IntStream.range(0, arrayCoeficientes.size())
				.map(x -> (digitosInicialesTMP.get(x) * finalArrayCoeficientes.get(x)))
				.forEach(consolidadodMultiplicacionIndiceConeficiente::addAndGet);

		int residuo = consolidadodMultiplicacionIndiceConeficiente.get() % 11;
		int resultado;

		if (residuo == 0) {
			resultado = 0;
		} else {
			resultado = (11 - residuo);
		}

		if (resultado != digitoVerificador) {
			throw new Exception("Dígitos iniciales no validan contra Dígito Idenficador");
		}

		return true;
	}

	//////

	public static String validadorDeCedula(String strTipoDeIdentificacion) {

		// Verifica Que sea mayor la cantidad de numeros no debe ser diferente a 10
		if (strTipoDeIdentificacion.length() != 10) {
			return "Error en el tamaño de la identificacion";
		} else {

			// Substring que lee los dos primeros digitos de la cedula
			int provincia = Integer.parseInt(strTipoDeIdentificacion.substring(0, 2));

			// Verifica que no sean mayor a 24 ni menor que 0
			if ((provincia > 24) && (provincia < 0)) {
				return "Error codigo de provincia";
			} else {

				// Substring que lee el tercerDigito de la Verificacion
				int tercerD = Integer.parseInt(strTipoDeIdentificacion.substring(2, 3));

				if ((tercerD > 6)) {

					return "Error tecer digito de la verificacion";

				} else {

					String primerD = strTipoDeIdentificacion.substring(0, 1);
					String SegundoD = strTipoDeIdentificacion.substring(1, 2);
					String TercerD = strTipoDeIdentificacion.substring(2, 3);
					String CuartoD = strTipoDeIdentificacion.substring(3, 4);
					String CincoD = strTipoDeIdentificacion.substring(4, 5);
					String SeisD = strTipoDeIdentificacion.substring(5, 6);
					String SieteD = strTipoDeIdentificacion.substring(6, 7);
					String OchoD = strTipoDeIdentificacion.substring(7, 8);
					String NueveD = strTipoDeIdentificacion.substring(8, 9);

					int[] imp = new int[4];

					imp[0] = Integer.parseInt(SegundoD) * 1;

					imp[1] = Integer.parseInt(CuartoD) * 1;

					imp[2] = Integer.parseInt(SeisD) * 1;

					imp[3] = Integer.parseInt(OchoD) * 1;

					int SumaI = imp[0] + imp[1] + imp[2] + imp[3];

					int[] par = new int[5];
					par[0] = Integer.parseInt(primerD) * 2;

					if (par[0] > 9) {

						par[0] = par[0] - 9;

					}

					par[1] = Integer.parseInt(TercerD) * 2;

					if (par[1] > 9) {

						par[1] = par[1] - 9;

					}

					par[2] = Integer.parseInt(CincoD) * 2;

					if (par[2] > 9) {

						par[2] = par[2] - 9;

					}
					par[3] = Integer.parseInt(SieteD) * 2;

					if (par[3] > 9) {

						par[3] = par[3] - 9;

					}
					par[4] = Integer.parseInt(NueveD) * 2;

					if (par[4] > 9) {
						par[4] = par[4] - 9;
					}

					int SumaP = par[0] + par[1] + par[2] + par[3] + par[4];

					int SumaT = SumaI + SumaP;

					int S = SumaT % 10;

					if (S > 0) {

						S = 10 - S;

					}
					int UltimoD = Integer.parseInt(strTipoDeIdentificacion.substring(9));
					// SI LA RESTA ES IGUAL AL DIGITO VERIFICADOR, SE VALIDA, 0 , SI EL RESIDUO DA 0
					// , SE VALIDA TAMBIEN
					if (S == UltimoD || S == 0) {

						return "T";
					}

				}

			}
			return "Error en la cédula";
		}

	}

	public static String ValidarRuc(String strTipoDeIdentificacion) {

		// Se verifica que solo pueden haber 13 digitos
		if (strTipoDeIdentificacion.length() != 13) {
			return "Error en el tamaño del RUC";
		} else {

			// Se leen los dos primeros digitos de el ruc
			int provincia = Integer.parseInt(strTipoDeIdentificacion.substring(0, 2));

			// Se Verifica que los dos primeros digitos no pueden ser mayores a 24
			if ((provincia > 24) && (provincia < 0)) {

				return "Error codigo de provincia";

			} else {

			}
			// Se lee solamente el tercer digito de el ruc
			int tercerDigito = Integer.parseInt(strTipoDeIdentificacion.substring(2, 3));

			// Se verifica que no sea mayor a 9 ni menor a 6
			if ((tercerDigito > 7 && (tercerDigito) != 9)) {

				return "Error en el tercer digio de la verificación";

			}

			int UltimosDigitos1 = Integer.parseInt(strTipoDeIdentificacion.substring(10, 11));

			int UltimoDigito2 = Integer.parseInt(strTipoDeIdentificacion.substring(11, 12));

			int UltimoDigito3 = Integer.parseInt(strTipoDeIdentificacion.substring(12, 13));

			if (UltimosDigitos1 != 0) {

				return "Error 1";

			} else if (UltimoDigito2 != 0) {

				return "Error 2";

			} else if (UltimoDigito3 != 1) {

				return "Error 3";
			}

			// Se leen todos los digitos menos el ultimo, el cual es el verificador

			if (tercerDigito == 9) {

				String primerD = strTipoDeIdentificacion.substring(0, 1);

				String SegundoD = strTipoDeIdentificacion.substring(1, 2);

				String TercerD = strTipoDeIdentificacion.substring(2, 3);

				String CuartoD = strTipoDeIdentificacion.substring(3, 4);

				String CincoD = strTipoDeIdentificacion.substring(4, 5);

				String SeisD = strTipoDeIdentificacion.substring(5, 6);

				String SieteD = strTipoDeIdentificacion.substring(6, 7);

				String OchoD = strTipoDeIdentificacion.substring(7, 8);

				String NueveD = strTipoDeIdentificacion.substring(8, 9);

				int[] operaciones = new int[9];

				operaciones[0] = Integer.parseInt(primerD) * 4;

				operaciones[1] = Integer.parseInt(SegundoD) * 3;

				operaciones[2] = Integer.parseInt(TercerD) * 2;

				operaciones[3] = Integer.parseInt(CuartoD) * 7;

				operaciones[4] = Integer.parseInt(CincoD) * 6;

				operaciones[5] = Integer.parseInt(SeisD) * 5;

				operaciones[6] = Integer.parseInt(SieteD) * 4;

				operaciones[7] = Integer.parseInt(OchoD) * 3;

				operaciones[8] = Integer.parseInt(NueveD) * 2;

				int Suma = operaciones[0] + operaciones[1] + operaciones[2] + operaciones[3] + operaciones[4]
						+ operaciones[5] + operaciones[6] + operaciones[7] + operaciones[8];

				int Residuo = Suma % 11;

				int Resta = 11 - Residuo;

				String DigitoVerificador = strTipoDeIdentificacion.substring(9, 10);

				// SI LA RESTA ES IGUAL AL DIGITO VERIFICADOR, SE VALIDA, 0 , SI EL RESIDUO DA 0
				// , SE VALIDA TAMBIEN
				if (Resta == Integer.parseInt(DigitoVerificador) || Residuo == 0) {

					return "T";

				}

			} else if (tercerDigito == 6 || tercerDigito < 6) {

				String primerD = strTipoDeIdentificacion.substring(0, 1);

				String SegundoD = strTipoDeIdentificacion.substring(1, 2);

				String TercerD = strTipoDeIdentificacion.substring(2, 3);

				String CuartoD = strTipoDeIdentificacion.substring(3, 4);

				String CincoD = strTipoDeIdentificacion.substring(4, 5);

				String SeisD = strTipoDeIdentificacion.substring(5, 6);

				String SieteD = strTipoDeIdentificacion.substring(6, 7);

				String OchoD = strTipoDeIdentificacion.substring(7, 8);

				String NueveD = strTipoDeIdentificacion.substring(8, 9);

				int[] operaciones = new int[8];
				operaciones[0] = Integer.parseInt(primerD) * 3;

				operaciones[1] = Integer.parseInt(SegundoD) * 2;

				operaciones[2] = Integer.parseInt(TercerD) * 7;

				operaciones[3] = Integer.parseInt(CuartoD) * 6;

				operaciones[4] = Integer.parseInt(CincoD) * 5;

				operaciones[5] = Integer.parseInt(SeisD) * 4;

				operaciones[6] = Integer.parseInt(SieteD) * 3;

				operaciones[7] = Integer.parseInt(OchoD) * 2;

				int Suma = operaciones[0] + operaciones[1] + operaciones[2] + operaciones[3] + operaciones[4]
						+ operaciones[5] + operaciones[6] + operaciones[7];

				int Residuo = Suma % 11;

				int Resta = 11 - Residuo;

				// Se lee el ultimo digito de la cedula
				String DigitoVerificador = strTipoDeIdentificacion.substring(9, 10);

				// SI LA RESTA ES IGUAL AL DIGITO VERIFICADOR, SE VALIDA, 0 , SI EL RESIDUO DA 0
				// , SE VALIDA TAMBIEN
				if (Resta == Integer.parseInt(DigitoVerificador) || Residuo == 0) {
					return "T";

				} else {

					int[] imp = new int[4];

					imp[0] = Integer.parseInt(SegundoD) * 1;

					imp[1] = Integer.parseInt(CuartoD) * 1;

					imp[2] = Integer.parseInt(SeisD) * 1;

					imp[3] = Integer.parseInt(OchoD) * 1;

					// Se suman todos los arreglos
					int SumaI = imp[0] + imp[1] + imp[2] + imp[3];

					// Se crea otro arreglo para meter todos los pares y multiplicarlos por dos
					int[] par = new int[5];

					/*
					 * Se realiza la condicion, si la multiplicacion realizada es mayor a 9 Entonces
					 * se le resta 9
					 */

					par[0] = Integer.parseInt(primerD) * 2;

					if (par[0] > 9) {

						par[0] = par[0] - 9;

					}

					par[1] = Integer.parseInt(TercerD) * 2;

					if (par[1] > 9) {

						par[1] = par[1] - 9;

					}

					par[2] = Integer.parseInt(CincoD) * 2;

					if (par[2] > 9) {

						par[2] = par[2] - 9;

					}
					par[3] = Integer.parseInt(SieteD) * 2;

					if (par[3] > 9) {

						par[3] = par[3] - 9;

					}
					par[4] = Integer.parseInt(NueveD) * 2;

					if (par[4] > 9) {

						par[4] = par[4] - 9;
					}

					// Se suman todos los pares
					int SumaP = par[0] + par[1] + par[2] + par[3] + par[4];

					// Se suman los pares e impares
					int SumaT = SumaI + SumaP;

					// Se le saca el residuo a la suma
					int S = SumaT % 10;

					// Si el residuo es mayor a 0, se le resta 10
					if (S > 0) {

						S = 10 - S;

					}

					// Se lee el ultimo digito de la cedula
					int UltimoD = Integer.parseInt(strTipoDeIdentificacion.substring(9, 10));

					// SI LA RESTA ES IGUAL AL DIGITO VERIFICADOR, SE VALIDA, o , SI EL RESIDUO DA 0
					// , SE VALIDA TAMBIEN
					if (S == UltimoD || S == 0) {

						return "T";

					} else {

						return "Error en el RUC";
					}

				}
			}
		}
		return "Error en el RUC";
	}

}