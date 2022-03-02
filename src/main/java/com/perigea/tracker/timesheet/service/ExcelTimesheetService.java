package com.perigea.tracker.timesheet.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perigea.tracker.commons.dto.NotaSpeseDto;
import com.perigea.tracker.commons.dto.TimesheetDataWrapper;
import com.perigea.tracker.commons.dto.TimesheetEntryDto;
import com.perigea.tracker.commons.dto.TimesheetResponseDto;
import com.perigea.tracker.commons.dto.wrapper.TimesheetExcelWrapper;
import com.perigea.tracker.commons.enums.EGiorno;
import com.perigea.tracker.commons.enums.EMese;
import com.perigea.tracker.commons.exception.TimesheetException;

/**
 * TODO calcolo dei rimborsi kilometrici con i dati contenuti in InfoAutoDto,
 * capire la differenza tra CostoNotaSpeseType.KILOMETRI e
 * CostoNotaSpeseType.RIMBORSO_KILOMETRICO e come legarli ai valori del modello
 * degli economics
 */
@Service
public class ExcelTimesheetService {

	private static final String TIMESHEET_NAME = "Timesheet";
	private static final String NOTE_SPESE_NAME = "NoteSpesa";
	
	@Autowired
	private Logger logger;

	public byte[] createExcelTimesheet(TimesheetExcelWrapper timesheetExcelWrapper) {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		XSSFWorkbook workbook = new XSSFWorkbook();

		try {
			TimesheetDataWrapper dataWrapper = buildTimesheetData(timesheetExcelWrapper);
			
			XSSFCellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			style.setFont(font);
			buildTimesheet(dataWrapper, style, workbook);
			buildNoteSpese(dataWrapper, style, workbook);


			workbook.write(bos);
			bos.close();
			workbook.close();
			logger.info("Complete");
			return bos.toByteArray();
		} catch (Exception ex) {
			throw new TimesheetException(ex.getMessage());
		} finally {
			try {
				bos.close();
				workbook.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	private void buildNoteSpese(TimesheetDataWrapper timesheetDataWrapper, XSSFCellStyle style, XSSFWorkbook workbook) {
		XSSFSheet secondSheet = workbook.createSheet(NOTE_SPESE_NAME);
		buildHeader(timesheetDataWrapper,secondSheet,style, timesheetDataWrapper.getRefNamesNoteSpese());
		
		XSSFRow eightRow = secondSheet.createRow(10);
		eightRow.createCell(0).setCellValue("Data");
		eightRow.getCell(0).setCellStyle(style);
		eightRow.createCell(1).setCellValue("Commessa");
		eightRow.getCell(1).setCellStyle(style);
		eightRow.createCell(2).setCellValue("Descrizione");
		eightRow.getCell(2).setCellStyle(style);
		eightRow.createCell(3).setCellValue("Aereo");
		eightRow.getCell(3).setCellStyle(style);
		eightRow.createCell(4).setCellValue("Alloggi");
		eightRow.getCell(4).setCellStyle(style);
		eightRow.createCell(5).setCellValue("Trasporti e carburante");
		eightRow.getCell(5).setCellStyle(style);
		eightRow.createCell(6).setCellValue("Pasti");
		eightRow.getCell(6).setCellStyle(style);
		eightRow.createCell(7).setCellValue("Conferenze e seminari");
		eightRow.getCell(7).setCellStyle(style);
		eightRow.createCell(8).setCellValue("Kilometri");
		eightRow.getCell(8).setCellStyle(style);
		eightRow.createCell(9).setCellValue("Rimborso Kilometrico");
		eightRow.getCell(9).setCellStyle(style);
		eightRow.createCell(10).setCellValue("Spese varie");
		eightRow.getCell(10).setCellStyle(style);
		eightRow.createCell(11).setCellValue("Cambio");
		eightRow.getCell(11).setCellStyle(style);
		eightRow.createCell(12).setCellValue("Importo €");
		eightRow.getCell(12).setCellStyle(style);
		
		Map<String, List<TimesheetEntryDto>> entries = timesheetDataWrapper.getEntries();
		Counter counter = new Counter();
		counter.i = 11;
		entries.forEach((k,v) -> {	
			for(TimesheetEntryDto t: v) {	
				for(NotaSpeseDto n: t.getNoteSpesa()) {
					
					XSSFRow rowNote = secondSheet.createRow(counter.i);
					
					XSSFCellStyle styleRowNote = workbook.createCellStyle();
					styleRowNote.setAlignment(HorizontalAlignment.CENTER);
					XSSFFont font2 = workbook.createFont();
					styleRowNote.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					styleRowNote.setBorderBottom(BorderStyle.MEDIUM);
					styleRowNote.setBorderTop(BorderStyle.MEDIUM);
					styleRowNote.setBorderRight(BorderStyle.MEDIUM);
					
					if (rowNote.getRowNum() % 2 == 0) {
						styleRowNote.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
						font2.setColor(IndexedColors.WHITE.getIndex());
						styleRowNote.setFont(font2);
					}else {
						styleRowNote.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
						styleRowNote.setFont(font2);
					}
					for(int i = 0 ; i < 13; i++) {
						rowNote.createCell(i).setCellStyle(styleRowNote);
					}
					
					rowNote.getCell(0).setCellValue( LocalDate.of(timesheetDataWrapper.getAnno(), timesheetDataWrapper.getMese().getMonthId(), t.getGiorno()).toString());
					rowNote.getCell(1).setCellValue(t.getCodiceCommessa());
					rowNote.getCell(2).setCellValue(t.getDescrizioneCommessa());
						
					for(int i = 3; i < 11; i++) {
						if(secondSheet.getRow(10).getCell(i).getStringCellValue().equalsIgnoreCase(n.getCostoNotaSpese().name())) {
							rowNote.getCell(i).setCellValue(n.getImporto());
						}
					}

					rowNote.getCell(9).setCellFormula("I"+(rowNote.getRowNum()+1)+"*"+"H7");
					rowNote.getCell(12).setCellFormula("SUM(D"+(rowNote.getRowNum()+1)+":H"+(rowNote.getRowNum()+1)+")"+"+J"+(rowNote.getRowNum()+1)+"+K"+(rowNote.getRowNum()+1));
					counter.i++;
				}
			}
		});
		int lastRowNum =secondSheet.getLastRowNum();
		
		XSSFCellStyle styleRowTot = workbook.createCellStyle();
		styleRowTot.setAlignment(HorizontalAlignment.CENTER);
		styleRowTot.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		styleRowTot.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleRowTot.setBorderBottom(BorderStyle.MEDIUM);
		styleRowTot.setBorderTop(BorderStyle.MEDIUM);
		styleRowTot.setBorderRight(BorderStyle.MEDIUM);
		styleRowTot.setBorderLeft(BorderStyle.MEDIUM);
		
//Non essendoci dati in arrivo la riga e commentata per una futura implementazione
//		XSSFRow rowAnticipi = secondSheet.createRow(lastRowNum+5);
//		rowAnticipi.createCell(10).setCellValue("Anticipi");
//		rowAnticipi.getCell(10).setCellStyle(style);
		
		XSSFRow rowTotali = secondSheet.createRow(lastRowNum+2);
		rowTotali.createCell(11).setCellValue("Totale:");
		rowTotali.getCell(11).setCellStyle(styleRowTot);

		rowTotali.createCell(12).setCellFormula("SUM(M12:M"+(lastRowNum+1)+")"/*+"-M"+(rowAnticipi.getRowNum()+1)*/);
		rowTotali.getCell(12).setCellStyle(styleRowTot);
		

		for (int columnIndex = 0; columnIndex < 50; columnIndex++) {
			secondSheet.autoSizeColumn(columnIndex);
		}

	}


	private void buildTimesheet(TimesheetDataWrapper timesheetDataWrapper, XSSFCellStyle style, XSSFWorkbook workbook) {
		XSSFSheet sheet = workbook.createSheet(TIMESHEET_NAME);
		buildHeader(timesheetDataWrapper,sheet,style, timesheetDataWrapper.getRefNamesTimesheet());
		
		XSSFRow eightRow = sheet.createRow(8);
		eightRow.createCell(0).setCellValue("Cliente");
		eightRow.getCell(0).setCellStyle(style);
		eightRow.createCell(1).setCellValue("Trasferta");
		eightRow.getCell(1).setCellStyle(style);
		eightRow.createCell(2).setCellValue("Commessa");
		eightRow.getCell(2).setCellStyle(style);
		eightRow.createCell(3).setCellValue("Tipo");
		eightRow.getCell(3).setCellStyle(style);
		eightRow.createCell(4).setCellValue("Descrizione");
		eightRow.getCell(4).setCellStyle(style);
		XSSFRow rowDaysOfWeek = sheet.createRow(7);
		Integer totalsColumnIndex = 0;
		EGiorno giornoSettimana = null;
		
		//Creazione riferimenti per i giorni
		for (int i = 0; i < EMese.getDays(timesheetDataWrapper.getMese().getMonthId(), timesheetDataWrapper.getAnno()); i++) {
			LocalDate date = LocalDate.of(timesheetDataWrapper.getAnno(), timesheetDataWrapper.getMese().getMonthId(), 1 + i);
			
			style.setBorderBottom(BorderStyle.MEDIUM);
			style.setBorderLeft(BorderStyle.MEDIUM);
			style.setBorderTop(BorderStyle.MEDIUM);
			style.setBorderRight(BorderStyle.MEDIUM);
			giornoSettimana = EGiorno.getGiorno(date.getDayOfWeek());
			eightRow.createCell(5 + i).setCellValue(giornoSettimana.getInitial());
			eightRow.getCell(5 + i).setCellStyle(style);
			rowDaysOfWeek.createCell(5 + i).setCellValue(1 + i);
			rowDaysOfWeek.getCell(5 + i).setCellStyle(style);
			totalsColumnIndex = (6 + i);
		}
		eightRow.createCell(totalsColumnIndex).setCellValue("Ore Totali");
		eightRow.getCell(totalsColumnIndex).setCellStyle(style);
		eightRow.createCell(totalsColumnIndex + 1).setCellValue("Giorni Totali");
		eightRow.getCell(totalsColumnIndex + 1).setCellStyle(style);
		
		//Ciclo di creazione delle row delle commesse (per ogni commessa una row)
		Map<String, List<TimesheetEntryDto>> entries = timesheetDataWrapper.getEntries();
		Counter counter = new Counter();
		counter.i = 9;
		entries.forEach((k,v) -> {
			Integer giorniTotaliMese = EMese.getDays(timesheetDataWrapper.getMese().getMonthId(), timesheetDataWrapper.getAnno());
			String lastColumnLetter = CellReference.convertNumToColString(giorniTotaliMese+4);
			XSSFRow row = sheet.createRow(counter.i);
			
			//Creazione style per le righe delle commesse
			XSSFCellStyle style2 = workbook.createCellStyle();
			style2.setAlignment(HorizontalAlignment.CENTER);
			XSSFFont font2 = workbook.createFont();
			if (row.getRowNum() % 2 == 0) {
				style2.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
				font2.setColor(IndexedColors.WHITE.getIndex());
				style2.setFont(font2);
			}else {
				style2.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
				style2.setFont(font2);
			}
			style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style2.setBorderBottom(BorderStyle.MEDIUM);
			style2.setBorderTop(BorderStyle.MEDIUM);
			style2.setBorderRight(BorderStyle.MEDIUM);
			
			for(int i = 0 ; i < 38; i++) {
				row.createCell(i).setCellStyle(style2);
			}
				
			row.getCell(0).setCellValue(v.get(0).getRagioneSociale());
			row.getCell(1).setCellValue(v.get(0).getTrasferta());
			row.getCell(2).setCellValue(k);
			row.getCell(3).setCellValue(v.get(0).getTipoCommessa().getDescrizione());
			row.getCell(4).setCellValue(v.get(0).getDescrizioneCommessa());
			
			//Ciclo per inserimento ore in base al giorno
			for(TimesheetEntryDto t: v) {
				row.getCell(t.getGiorno()+4).setCellValue(t.getOre());
			}
			//creazione formule totali parziali
			row.getCell(giorniTotaliMese+5).setCellFormula("SUM(F"+(row.getRowNum()+1)+":"+lastColumnLetter+(row.getRowNum()+1)+")");
			row.getCell(giorniTotaliMese+6).setCellFormula(CellReference.convertNumToColString(giorniTotaliMese+5)+(row.getRowNum()+1)+"/8");
			
			counter.i++;
		});
		
		//Creazione formule totali
		Integer giorniTotaliMese = EMese.getDays(timesheetDataWrapper.getMese().getMonthId(), timesheetDataWrapper.getAnno());
		String lastColumnLetter = CellReference.convertNumToColString(giorniTotaliMese+5);
		XSSFRow row = sheet.createRow(sheet.getLastRowNum()+2);
		
		//Style per la riga sei totali
		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style2.setBorderBottom(BorderStyle.MEDIUM);
		style2.setBorderTop(BorderStyle.MEDIUM);
		style2.setBorderRight(BorderStyle.MEDIUM);
		style2.setBorderLeft(BorderStyle.MEDIUM);
		
		for(int i = 0; i< giorniTotaliMese; i++) {
			row.createCell(i+5).setCellFormula("SUM("+CellReference.convertNumToColString(i+5)+"10:"+CellReference.convertNumToColString(i+5)+(row.getRowNum()-1)+")");
			style2.setAlignment(HorizontalAlignment.CENTER);
			style2.getFont().setBold(true);
			row.getCell(i+5).setCellStyle(style2);
		}
		
		row.createCell(4).setCellValue("Totali");
		row.getCell(4).setCellStyle(style2);
		row.createCell(giorniTotaliMese+5).setCellFormula("SUM("+lastColumnLetter+"10:"+lastColumnLetter+(row.getRowNum()-1)+")");
		row.getCell(giorniTotaliMese+5).setCellStyle(style2);
		row.createCell(giorniTotaliMese+6).setCellFormula("SUM("+CellReference.convertNumToColString(giorniTotaliMese+6)+"10:"+CellReference.convertNumToColString(giorniTotaliMese+6)+(row.getRowNum()-1)+")");
		row.getCell(giorniTotaliMese+6).setCellStyle(style2);
		
		for (int columnIndex = 0; columnIndex < 50; columnIndex++) {
			sheet.autoSizeColumn(columnIndex);
		}
	}

	private void buildHeader(TimesheetDataWrapper timesheetDataWrapper, XSSFSheet sheet, XSSFCellStyle style, LinkedHashMap<String, String> refNames) {
		try {
			
			//Anchor per il logo
			InputStream logoStream = getClass().getClassLoader().getResourceAsStream("perigea_logo_color.png");
			byte[] logoBytes = IOUtils.toByteArray(logoStream);
			int logo = sheet.getWorkbook().addPicture(logoBytes, Workbook.PICTURE_TYPE_JPEG);
			XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
			XSSFClientAnchor imagesAnchor = new XSSFClientAnchor();
			imagesAnchor.setCol1(0);
			imagesAnchor.setCol2(5);
			imagesAnchor.setRow1(0);
			imagesAnchor.setRow2(6);
			drawing.createPicture(imagesAnchor, logo);

			XSSFCellStyle styleHeader = sheet.getWorkbook().createCellStyle();
			XSSFFont fontHeader = sheet.getWorkbook().createFont();
			fontHeader.setBold(true);
			styleHeader.setFont(fontHeader);
			
			//Merge per le celle dei dati generali in base allo sheet
			if(sheet.getSheetName().equals(TIMESHEET_NAME)) {
				for(int i = 1; i <= refNames.size(); i++ ) {
					sheet.addMergedRegion(CellRangeAddress.valueOf("F"+i+":K"+i));
					sheet.addMergedRegion(CellRangeAddress.valueOf("L"+i+":AJ"+i));
				}
			}else {
				for(int i = 1; i <= refNames.size(); i++ ) {
					sheet.addMergedRegion(CellRangeAddress.valueOf("F"+i+":G"+i));
					sheet.addMergedRegion(CellRangeAddress.valueOf("H"+i+":M"+i));
				}
			}
			
			//Inserimento dei dati generali in base allo sheet
			Counter counter = new Counter();
			if(sheet.getSheetName().equals(TIMESHEET_NAME)) {
				refNames.forEach((k,v) -> {
					XSSFRow row = sheet.createRow(counter.i);
					row.createCell(5).setCellValue(k);
					row.getCell(5).setCellStyle(styleHeader);
					row.createCell(11).setCellValue(v);
					row.getCell(11).setCellStyle(styleHeader);;
					counter.i++;
				} );
			}else {
				refNames.forEach((k,v) -> {
					styleHeader.setAlignment(HorizontalAlignment.LEFT);
					XSSFRow row = sheet.createRow(counter.i);
					row.createCell(5).setCellValue(k);
					row.getCell(5).setCellStyle(styleHeader);
					row.createCell(7).setCellValue(v);
					row.getCell(7).setCellStyle(styleHeader);;
					counter.i++;
				} );
				XSSFRow row = sheet.getRow(6);
				row.getCell(7).setCellValue(Double.parseDouble(refNames.get("Rimborso Km in €")));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Creazione Wrapper per il raccoglimento dei valori interessati agli sheet
	private TimesheetDataWrapper buildTimesheetData(TimesheetExcelWrapper timesheetExcelWrapper) {
		TimesheetResponseDto timesheet = timesheetExcelWrapper.getTimesheet();

		return TimesheetDataWrapper.builder().codicePersona(timesheet.getCodicePersona())
				.entries(getTimesheetEntriesDto(timesheetExcelWrapper))
				.nome(timesheetExcelWrapper.getAngrafica().getNome())
				.cognome(timesheetExcelWrapper.getAngrafica().getCognome())
				.infoAuto(timesheetExcelWrapper.getInfoAuto()).anno(timesheet.getAnno()).mese(timesheet.getMese())
				.statoTimesheet(timesheet.getStatoRichiesta().name()).build();
	}

	//Ciclo per la raccolta delle entries per commessa
	private Map<String, List<TimesheetEntryDto>> getTimesheetEntriesDto(TimesheetExcelWrapper timesheetExcelWrapper) {
		TimesheetResponseDto timesheet = timesheetExcelWrapper.getTimesheet();
		Map<String, List<TimesheetEntryDto>> entries = new HashMap<>();
		for (TimesheetEntryDto t : timesheet.getEntries()) {
			if (!entries.containsKey(t.getCodiceCommessa())) {
				List<TimesheetEntryDto> tsList = new ArrayList<TimesheetEntryDto>();
				for (TimesheetEntryDto ts : timesheet.getEntries()) {
					if (ts.getCodiceCommessa().equals(t.getCodiceCommessa())) {
						tsList.add(ts);
					}
				}
				entries.put(t.getCodiceCommessa(), tsList);
			}
		}
		return entries;
	}
	
	//Classe di utilita conteggio
	private class Counter{
		public int i = 0;
	}

}