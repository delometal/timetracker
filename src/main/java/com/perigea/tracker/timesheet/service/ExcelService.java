package com.perigea.tracker.timesheet.service;

import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.timesheet.dto.TimesheetEntryDto;
import com.perigea.tracker.timesheet.dto.wrapper.TimesheetExcelWrapper;
import com.perigea.tracker.timesheet.enums.EMese;

@Service
public class ExcelService {

	@Autowired
	private Logger logger;

	public void statementTimesheet(TimesheetExcelWrapper timesheetExcelWrapper) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Timesheet excel");
			
			XSSFRow thirdRow = sheet.createRow(0);
			thirdRow.createCell(3).setCellValue("codice_persona");
			XSSFRow oneRow = sheet.createRow(1);
			oneRow.createCell(3).setCellValue("nome");
			XSSFRow twoRow = sheet.createRow(2);
			twoRow.createCell(3).setCellValue("cognome");
			XSSFRow threeRow = sheet.createRow(3);
			threeRow.createCell(3).setCellValue("anno_di_riferimento");
			XSSFRow fourRow = sheet.createRow(4);
			fourRow.createCell(3).setCellValue("mese_di_riferimento");
			XSSFRow fiveRow = sheet.createRow(6);
			fiveRow.createCell(1).setCellValue("trasferta");

			String codicePersona = timesheetExcelWrapper.getTimesheet().getCodicePersona();
			String nome = timesheetExcelWrapper.getDipendente().getNome();
			String cognome = timesheetExcelWrapper.getDipendente().getCognome();
			Integer annoDiRiferimento = timesheetExcelWrapper.getTimesheet().getAnno();
			Integer meseDiRiferimento = timesheetExcelWrapper.getTimesheet().getMese();
			for (TimesheetEntryDto t : timesheetExcelWrapper.getTimesheet().getEntries()) {
				Boolean trasferta = t.getTrasferta();
				XSSFRow sixRow = sheet.createRow(7);
				sixRow.createCell(1).setCellValue(trasferta);
			}
			thirdRow.createCell(5).setCellValue(codicePersona);
			oneRow.createCell(5).setCellValue(nome);
			twoRow.createCell(5).setCellValue(cognome);
			threeRow.createCell(5).setCellValue(annoDiRiferimento);
			fourRow.createCell(5).setCellValue(meseDiRiferimento);
			XSSFRow rowDays = sheet.createRow(5);
			for (int i = 0; i < EMese.getDays(timesheetExcelWrapper.getTimesheet().getMese()); i++) {
				rowDays.createCell(3 + i).setCellValue(1 + i);
			}

			FileOutputStream file = new FileOutputStream("C:\\Users\\Utente\\Documents\\Timesheet3.xlsx");
			workbook.write(file);
			workbook.close();
			file.close();
			logger.info("Complete");
		} catch (Exception e) {
			e.getMessage();
		}
	}
}


//	public void statementTimesheet(TimesheetExcelWrapper timesheetExcelWrapper) {
//		try {
//			XSSFWorkbook workbook = new XSSFWorkbook();
//			XSSFSheet sheet = workbook.createSheet("Timesheet excel");
//			XSSFRow row = sheet.createRow(0);
//			row.createCell(0).setCellValue("anno_di_riferimento");
//			row.createCell(1).setCellValue("mese_di_riferimento");
//			row.createCell(2).setCellValue("codice_persona");
//			row.createCell(3).setCellValue("ore_totali");
//			row.createCell(4).setCellValue("stato_time_sheet");
//			int r = 1;
//			Integer annoDiRiferimento = timesheetExcelWrapper.getTimesheet().getAnno();
//			Integer meseDiRiferimento = timesheetExcelWrapper.getTimesheet().getMese();
//			String codicePersona = timesheetExcelWrapper.getTimesheet().getCodicePersona();
//			Integer oreTotali = timesheetExcelWrapper.getTimesheet().getOreTotali();
//			String statoTimesheet = timesheetExcelWrapper.getTimesheet().getStatoRichiesta().toString();
//			String nome = timesheetExcelWrapper.getTimesheet().getNome();
//			String cognome = timesheetExcelWrapper.getTimesheet().getCognome();
//			row = sheet.createRow(r++);
//			row.createCell(0).setCellValue(annoDiRiferimento);
//			row.createCell(1).setCellValue(meseDiRiferimento);
//			row.createCell(2).setCellValue(codicePersona);
//			row.createCell(3).setCellValue(oreTotali);
//			row.createCell(4).setCellValue(statoTimesheet);
//
////			//parte relativa al timesheet giornaliero
//			XSSFRow secondRow = sheet.createRow(2);
//			secondRow.createCell(0).setCellValue("codice_commessa");
//			secondRow.createCell(1).setCellValue("giorno_di_riferimento");
//			secondRow.createCell(2).setCellValue("ore");
//			secondRow.createCell(3).setCellValue("trasferta");
//			r = r + 1;
//			for (TimesheetEntryDto t : timesheetExcelWrapper.getTimesheet().getEntries()) {
//				String codiceCommessa = t.getCodiceCommessa();
//				Integer giornoDiRiferimento = t.getGiorno();
//				Integer ore = t.getOre();
//				Boolean trasferta = t.getTrasferta();
//				secondRow = sheet.createRow(r++);
//				secondRow.createCell(0).setCellValue(codiceCommessa);
//				secondRow.createCell(1).setCellValue(giornoDiRiferimento);
//				secondRow.createCell(2).setCellValue(ore);
//				secondRow.createCell(3).setCellValue(trasferta);
//			}
//
////			//parte relativa all'utente (codicePersona, nome, cognome)
//			XSSFRow thirdRow = sheet.createRow(4);
//			thirdRow.createCell(0).setCellValue("codice_persona");
//			thirdRow.createCell(1).setCellValue("nome");
//			thirdRow.createCell(2).setCellValue("cognome");
//			r = r + 1;
//			codicePersona = timesheetExcelWrapper.getTimesheet().getCodicePersona();
//			nome = timesheetExcelWrapper.getTimesheet().getNome();
//			cognome = timesheetExcelWrapper.getTimesheet().getCognome();
//			thirdRow = sheet.createRow(r++);
//			thirdRow.createCell(0).setCellValue(codicePersona);
//			thirdRow.createCell(1).setCellValue(nome);
//			thirdRow.createCell(2).setCellValue(cognome);
//
//			// parte relativa alla commessa
//			XSSFRow fourthRow = sheet.createRow(6);
//			fourthRow.createCell(0).setCellValue("codice_commessa");
//			fourthRow.createCell(1).setCellValue("tipo_commessa");
//			fourthRow.createCell(2).setCellValue("descrizione_commessa");
//			r = r + 1;
//			for (TimesheetEntryDto t : timesheetExcelWrapper.getTimesheet().getEntries()) {
//				String codiceCommessa = t.getCodiceCommessa();
//				String tipoCommessa = t.getTipoCommessa().toString();
//				String descrizioneCommessa = t.getDescrizioneCommessa();
//				thirdRow = sheet.createRow(r++);
//				thirdRow.createCell(0).setCellValue(codiceCommessa);
//				thirdRow.createCell(1).setCellValue(tipoCommessa);
//				thirdRow.createCell(2).setCellValue(descrizioneCommessa);
//			}
//
//			// parte relativa al cliente
//			XSSFRow fifthRow = sheet.createRow(8);
//			fifthRow.createCell(0).setCellValue("partita_iva");
//			fifthRow.createCell(1).setCellValue("ragione_sociale");
//			fifthRow.createCell(1).setCellValue("codice_fiscale");
//			r = r + 1;
//			for (TimesheetEntryDto t : timesheetExcelWrapper.getTimesheet().getEntries()) {
//				String codiceCommessa = t.getCodiceCommessa();
//				String tipoCommessa = t.getTipoCommessa().toString();
//				thirdRow = sheet.createRow(r++);
//				thirdRow.createCell(0).setCellValue(codiceCommessa);
//				thirdRow.createCell(1).setCellValue(tipoCommessa);
//			}
//		
//			XSSFRow rowDays = sheet.createRow(5);
//			for (int i = 0; i < EMese.getDays(timesheetExcelWrapper.getTimesheet().getMese()) + 1; i++) {
//				rowDays.createCell(3 + i).setCellValue(1 + i);
//			}
//
//			FileOutputStream file = new FileOutputStream("C:\\Users\\Utente\\Documents\\Timesheet.xlsx");
//			workbook.write(file);
//			workbook.close();
//			file.close();
//			logger.info("Complete");
//		} catch (Exception e) {
//			e.getMessage();
//		}
//	}
//}