package com.perigea.tracker.timesheet.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
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

import com.perigea.tracker.timesheet.dto.InfoAutoDto;
import com.perigea.tracker.timesheet.dto.NotaSpeseDto;
import com.perigea.tracker.timesheet.dto.TimesheetEntryDto;
import com.perigea.tracker.timesheet.dto.TimesheetResponseDto;
import com.perigea.tracker.timesheet.dto.wrapper.TimesheetExcelWrapper;
import com.perigea.tracker.timesheet.enums.CostoNotaSpeseType;
import com.perigea.tracker.timesheet.enums.EGiorno;
import com.perigea.tracker.timesheet.enums.EMese;
import com.perigea.tracker.timesheet.exception.TimesheetException;
import com.perigea.tracker.timesheet.utility.TSUtils;

/**
 * TODO calcolo dei rimborsi kilometrici con i dati contenuti in InfoAutoDto,
 * capire la differenza tra CostoNotaSpeseType.KILOMETRI e CostoNotaSpeseType.RIMBORSO_KILOMETRICO 
 * e come legarli ai valori del modello degli economics
 */
@Service
public class ExcelTimesheetService {

	@Autowired
	private Logger logger;

	public byte[] createExcelTimesheet(TimesheetExcelWrapper timesheetExcelWrapper) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		XSSFWorkbook workbook = new XSSFWorkbook();
		try {
			TimesheetResponseDto timesheet = timesheetExcelWrapper.getTimesheet();
			String codicePersona = timesheet.getCodicePersona();
			String nome = timesheetExcelWrapper.getAngrafica().getNome();
			String cognome = timesheetExcelWrapper.getAngrafica().getCognome();
			InfoAutoDto infoAuto = timesheetExcelWrapper.getInfoAuto();
			Integer anno = timesheet.getAnno();
			Integer mese = timesheet.getMese();
			String statoTimesheet = timesheet.getStatoRichiesta().getDescrizione();
			EMese eMese = EMese.getByMonthId(mese);
			
			XSSFSheet sheet = workbook.createSheet("Timesheet");
			XSSFSheet secondSheet = workbook.createSheet("NoteSpesa");
			XSSFCellStyle style = workbook.createCellStyle();
			
			XSSFFont defaultFont = workbook.createFont();
			defaultFont.setBold(true);
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			style.setFont(font);

			InputStream logoStream = ExcelTimesheetService.class.getClassLoader().getResourceAsStream(TSUtils.PERIGEA_LOGO_COLOR);
			byte[] logoBytes = IOUtils.toByteArray(logoStream);
			int logoTimesheet = workbook.addPicture(logoBytes, Workbook.PICTURE_TYPE_JPEG);
			XSSFDrawing drawing1 = (XSSFDrawing) secondSheet.createDrawingPatriarch();
			XSSFClientAnchor imagesAnchor1 = new XSSFClientAnchor();
			imagesAnchor1.setCol1(9);
			imagesAnchor1.setCol2(13);
			imagesAnchor1.setRow1(0);
			imagesAnchor1.setRow2(4);
			drawing1.createPicture(imagesAnchor1, logoTimesheet);

			secondSheet.addMergedRegion(CellRangeAddress.valueOf("B1:C1"));
			secondSheet.addMergedRegion(CellRangeAddress.valueOf("B2:C2"));
			secondSheet.addMergedRegion(CellRangeAddress.valueOf("B3:C3"));
			secondSheet.addMergedRegion(CellRangeAddress.valueOf("B4:C4"));
			secondSheet.addMergedRegion(CellRangeAddress.valueOf("B5:C5"));
			secondSheet.addMergedRegion(CellRangeAddress.valueOf("K23:L23"));
			secondSheet.addMergedRegion(CellRangeAddress.valueOf("K24:L24"));
			secondSheet.addMergedRegion(CellRangeAddress.valueOf("G24:H24"));

			XSSFRow Row0 = secondSheet.createRow(0);
			Row0.createCell(1).setCellValue("Nome");
			Row0.getCell(1).setCellStyle(style);
			Row0.createCell(3).setCellValue(nome + " " + cognome);
			Row0.getCell(3).setCellStyle(style);
			XSSFRow Row1 = secondSheet.createRow(1);
			Row1.createCell(1).setCellValue("Periodo");
			Row1.getCell(1).setCellStyle(style);
			
			Row1.createCell(3).setCellValue(eMese + "-" + anno);
			Row1.getCell(3).setCellStyle(style);
			XSSFRow Row2 = secondSheet.createRow(2);
			Row2.createCell(1).setCellValue("Rimborso Km in €");
			Row2.createCell(3).setCellValue((infoAuto.getRimborsoPerKm()==null)? 0.0f : infoAuto.getRimborsoPerKm());
			Row2.getCell(1).setCellStyle(style);
			XSSFRow Row3 = secondSheet.createRow(3);
			Row3.createCell(1).setCellValue("Auto");
			Row3.createCell(3).setCellValue((infoAuto.getModelloAuto()==null)? "" : infoAuto.getModelloAuto());
			Row3.getCell(1).setCellStyle(style);
			XSSFRow Row4 = secondSheet.createRow(4);
			Row4.createCell(1).setCellValue("Totale");
			Row4.getCell(1).setCellStyle(style);

			XSSFRow Row6 = secondSheet.createRow(6);
			Row6.createCell(1).setCellValue("Data");
			Row6.getCell(1).setCellStyle(style);
			Row6.createCell(2).setCellValue("Descrizione");
			Row6.getCell(2).setCellStyle(style);
			Row6.createCell(3).setCellValue("Aereo");
			Row6.getCell(3).setCellStyle(style);
			Row6.createCell(4).setCellValue("Alloggi");
			Row6.getCell(4).setCellStyle(style);
			Row6.createCell(5).setCellValue("Trasporti e Carburante");
			Row6.getCell(5).setCellStyle(style);
			Row6.createCell(6).setCellValue("Pasti");
			Row6.getCell(6).setCellStyle(style);
			Row6.createCell(7).setCellValue("Conferenze e seminari");
			Row6.getCell(7).setCellStyle(style);
			Row6.createCell(8).setCellValue("Kilometri");
			Row6.getCell(8).setCellStyle(style);
			Row6.createCell(9).setCellValue("Rimborso Kilometrico");
			Row6.getCell(9).setCellStyle(style);
			Row6.createCell(10).setCellValue("Spese varie");
			Row6.getCell(10).setCellStyle(style);
			Row6.createCell(11).setCellValue("Cambio");
			Row6.getCell(11).setCellStyle(style);
			Row6.createCell(12).setCellValue("Importo €");
			Row6.getCell(12).setCellStyle(style);
			XSSFRow RowAnticipi = secondSheet.createRow(22);
			RowAnticipi.createCell(10).setCellValue("Anticipi");
			RowAnticipi.getCell(10).setCellStyle(style);
			XSSFRow RowTotali = secondSheet.createRow(23);
			RowTotali.createCell(10).setCellValue("Totale:");
			RowTotali.getCell(10).setCellStyle(style);
			RowTotali.createCell(6).setCellValue("Totale Rimborso Kilometrico");
			RowTotali.getCell(6).setCellStyle(style);

			Double totale = 0.0;
			int logoNoteSpesa = workbook.addPicture(logoBytes, Workbook.PICTURE_TYPE_JPEG);
			XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
			XSSFClientAnchor imagesAnchor = new XSSFClientAnchor();
			imagesAnchor.setCol1(0);
			imagesAnchor.setCol2(2);
			imagesAnchor.setCol2(3);
			imagesAnchor.setCol2(4);
			imagesAnchor.setCol2(5);
			imagesAnchor.setRow1(0);
			imagesAnchor.setRow2(1);
			imagesAnchor.setRow2(2);
			imagesAnchor.setRow2(3);
			imagesAnchor.setRow2(4);
			imagesAnchor.setRow2(5);
			imagesAnchor.setRow2(6);
			drawing.createPicture(imagesAnchor, logoNoteSpesa);

			sheet.addMergedRegion(CellRangeAddress.valueOf("F1:K1"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("F2:K2"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("F3:K3"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("F4:K4"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("F5:K5"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("F6:K6"));

			sheet.addMergedRegion(CellRangeAddress.valueOf("L1:AJ1"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("L2:AJ2"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("L3:AJ3"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("L4:AJ4"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("L5:AJ5"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("L6:AJ6"));

			XSSFRow thirdRow = sheet.createRow(0);
			thirdRow.createCell(5).setCellValue("Codice");
			thirdRow.getCell(5).setCellStyle(style);

			XSSFRow oneRow = sheet.createRow(1);
			oneRow.createCell(5).setCellValue("Nome");
			oneRow.getCell(5).setCellStyle(style);

			XSSFRow twoRow = sheet.createRow(2);
			twoRow.createCell(5).setCellValue("Cognome");
			twoRow.getCell(5).setCellStyle(style);

			XSSFRow threeRow = sheet.createRow(3);
			threeRow.createCell(5).setCellValue("Anno");
			threeRow.getCell(5).setCellStyle(style);

			XSSFRow fourRow = sheet.createRow(4);
			fourRow.createCell(5).setCellValue("Mese");
			fourRow.getCell(5).setCellStyle(style);

			XSSFRow fiveRow = sheet.createRow(5);
			fiveRow.createCell(5).setCellValue("Stato");
			fiveRow.getCell(5).setCellStyle(style);

			XSSFRow eightRow = sheet.createRow(8);
			eightRow.createCell(1).setCellValue("Trasferta");
			eightRow.getCell(1).setCellStyle(style);
			eightRow.createCell(0).setCellValue("Cliente");
			eightRow.getCell(0).setCellStyle(style);
			eightRow.createCell(2).setCellValue("Commessa");
			eightRow.getCell(2).setCellStyle(style);
			eightRow.createCell(3).setCellValue("Tipo");
			eightRow.getCell(3).setCellStyle(style);
			eightRow.createCell(4).setCellValue("Descrizione");
			eightRow.getCell(4).setCellStyle(style);

			// key t.giorno, value t.giorno
			Map<Integer, Integer> mapGiorno = new HashMap<>();
			// key t.codiceCommessa, value t.codiceCommessa
			Map<Integer, String> mapCodiceCommessa = new HashMap<>();
			// key t.codiceCommessa, value t.ore
			Map<String, Integer> mapSumCommessa = new HashMap<>();
			// key t.codiceCommessa, value giorniTotaliPerRiga
			Map<String, Integer> mapGiorniPerRiga = new HashMap<>();
			Set<String> codiciCommessa = new HashSet<>();
			Set<Double> noteSpesa = new HashSet<>();
			timesheet.getEntries().forEach(r -> {
				codiciCommessa.add(r.getCodiceCommessa());
				r.getNoteSpesa().forEach(b -> {
					noteSpesa.add(b.getImporto());
				});
			});

			thirdRow.createCell(11).setCellValue(codicePersona);
			thirdRow.getCell(11).setCellStyle(style);
			oneRow.createCell(11).setCellValue(nome);
			oneRow.getCell(11).setCellStyle(style);
			twoRow.createCell(11).setCellValue(cognome);
			twoRow.getCell(11).setCellStyle(style);
			threeRow.createCell(11).setCellValue(anno + "");
			threeRow.getCell(11).setCellStyle(style);
			fiveRow.createCell(11).setCellValue(statoTimesheet);
			fiveRow.getCell(11).setCellStyle(style);
			fourRow.createCell(11).setCellValue(eMese.getDescription());
			fourRow.getCell(11).setCellStyle(style);
			XSSFRow rowDaysOfWeek = sheet.createRow(7);
			Integer totalsColumnIndex = 0;
			EGiorno giornoSettimana = null;
			for (int i = 0; i < EMese.getDays(timesheet.getMese(), timesheet.getAnno()); i++) {
				LocalDate date = LocalDate.of(anno, mese, 1 + i);
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
			int r = 8;
			Integer totalsRowIndex = r + codiciCommessa.size() + 1;
			XSSFRow totalRow = sheet.createRow(totalsRowIndex);
			totalRow.createCell(4).setCellValue("Totali");
			totalRow.getCell(4).setCellStyle(style);
			double index = 0;
			Integer oreTotaliPerMese = 0;
			Integer giorniTotaliPerMese = 0;
			int firstRowAvaiableNotaSpese = 7;

			XSSFRow row;

			for (TimesheetEntryDto t : timesheet.getEntries()) {

				// IMPLEMENTAZIONE NOTASPESE
				XSSFRow rowNotaSpese = secondSheet.createRow(firstRowAvaiableNotaSpese);
				CostoNotaSpeseType costoNotaSpeseType = null;
				double sumNotaSpese = 0;
				for (NotaSpeseDto n : t.getNoteSpesa()) {
					if (n != null) {
						firstRowAvaiableNotaSpese++;
					}
					Double importo = n.getImporto();
					String descrizione = t.getDescrizioneCommessa();
					rowNotaSpese.createCell(2).setCellValue(descrizione);
					if (costoNotaSpeseType == CostoNotaSpeseType.AEREO) {
						rowNotaSpese.createCell(3).setCellValue(importo);
						sumNotaSpese = sumNotaSpese + importo;
						totale += importo;
					} else if (costoNotaSpeseType == CostoNotaSpeseType.ALLOGGIO) {
						rowNotaSpese.createCell(4).setCellValue(importo);
						sumNotaSpese = sumNotaSpese + importo;
						totale += importo;
					} else if (costoNotaSpeseType == CostoNotaSpeseType.TRASPORTI_E_CARBURANTE) {
						rowNotaSpese.createCell(5).setCellValue(importo);
						sumNotaSpese = sumNotaSpese + importo;
						totale += importo;
					} else if (costoNotaSpeseType == CostoNotaSpeseType.PASTI) {
						rowNotaSpese.createCell(6).setCellValue(importo);
						sumNotaSpese = sumNotaSpese + importo;
						totale += importo;
					} else if (costoNotaSpeseType == CostoNotaSpeseType.CONFERENZE_E_SEMINARI) {
						rowNotaSpese.createCell(7).setCellValue(importo);
						sumNotaSpese = sumNotaSpese + importo;
						totale += importo;
					} else if (costoNotaSpeseType == CostoNotaSpeseType.KILOMETRI) {
						rowNotaSpese.createCell(8).setCellValue(importo);
						sumNotaSpese = sumNotaSpese + importo;
						totale += importo;
					} else if (costoNotaSpeseType == CostoNotaSpeseType.RIMBORSO_KILOMETRICO) {
						rowNotaSpese.createCell(9).setCellValue(importo);
						sumNotaSpese = sumNotaSpese + importo;
						totale += importo;
					} else {
						rowNotaSpese.createCell(10).setCellValue(importo);
						sumNotaSpese = sumNotaSpese + importo;
						totale += importo;
					}
					double totaleImportoNotaSpese = sumNotaSpese;
					for (int i = 0; i < t.getNoteSpesa().size(); i++) {
						rowNotaSpese.createCell(12).setCellValue(totaleImportoNotaSpese);
						RowTotali.createCell(12).setCellValue(totale);
					}
				}
				RowTotali.createCell(12).setCellValue(totale);

				String codiceCommessa = t.getCodiceCommessa();
				Integer giornoDiRiferimento = t.getGiorno();
				String tipoCommessa = t.getTipoCommessa().toString();
				String descrizioneCommessa = t.getDescrizioneCommessa();
				Integer ore = t.getOre();
				String cliente = t.getRagioneSociale();
				Boolean trasferta = t.getTrasferta();

				// implementazione del calcolo giornaliero totale delle ore
				if (!mapGiorno.containsKey(t.getGiorno())) {
					totalRow.createCell(t.getGiorno() + 4).setCellValue(t.getOre());
					mapGiorno.put(t.getGiorno(), t.getGiorno());
				}

				Integer oreTotaliPerRiga = 0;
				Integer giorniTotaliPerRiga = 0;
				// commesse singole, ore singole
				for (int j = 0; j < EMese.getDays(timesheet.getMese(), timesheet.getAnno()); j++) {
					Double giorno = rowDaysOfWeek.getCell(5 + j).getNumericCellValue();
					if (giornoDiRiferimento == giorno.intValue()) {
						if (mapCodiceCommessa.containsValue(t.getCodiceCommessa())) {
							// se è uguale il codice commessa
							XSSFCell formulaCell = null;
							XSSFCell cellGiorniTotaliPerRiga = null;
							for (var entry : mapSumCommessa.entrySet()) {
								if (entry.getKey().equals(t.getCodiceCommessa())) {
									oreTotaliPerRiga = entry.getValue() + t.getOre();
									entry.setValue(entry.getValue() + t.getOre());
								}
							}

							for (var entry : mapGiorniPerRiga.entrySet()) {
								if (entry.getKey().equals(t.getCodiceCommessa())) {
									giorniTotaliPerRiga = entry.getValue() + 1;
									entry.setValue(giorniTotaliPerRiga);
								}
							}

							for (var entry : mapCodiceCommessa.entrySet()) {
								if (entry.getValue().equals(t.getCodiceCommessa())) {
									row = sheet.getRow(entry.getKey());
									row.getCell(5 + j).setCellValue(ore);
									if (EMese.getDays(timesheet.getMese(),
											timesheet.getAnno()) == 28) {
										formulaCell = sheet.getRow(entry.getKey())
												.getCell(EMese.getDays(timesheet.getMese(),
														timesheet.getAnno()) + 1 + 4);
										formulaCell.setCellValue(oreTotaliPerRiga);
										cellGiorniTotaliPerRiga = sheet.getRow(entry.getKey())
												.getCell(EMese.getDays(timesheet.getMese(),
														timesheet.getAnno()) + 1 + 4 + 1);
										cellGiorniTotaliPerRiga.setCellValue(giorniTotaliPerRiga);
									}
									if (EMese.getDays(timesheet.getMese(),
											timesheet.getAnno()) == 29) {
										formulaCell = sheet.getRow(entry.getKey())
												.getCell(EMese.getDays(timesheet.getMese(),
														timesheet.getAnno()) + 1 + 4);
										formulaCell.setCellValue(oreTotaliPerRiga);
										cellGiorniTotaliPerRiga = sheet.getRow(entry.getKey())
												.getCell(EMese.getDays(timesheet.getMese(),
														timesheet.getAnno()) + 1 + 4 + 1);
										cellGiorniTotaliPerRiga.setCellValue(giorniTotaliPerRiga);
									} else if (EMese.getDays(timesheet.getMese(),
											timesheet.getAnno()) == 30) {
										formulaCell = sheet.getRow(entry.getKey())
												.getCell(EMese.getDays(timesheet.getMese(),
														timesheet.getAnno()) + 1 + 4);
										formulaCell.setCellValue(oreTotaliPerRiga);
										cellGiorniTotaliPerRiga = sheet.getRow(entry.getKey())
												.getCell(EMese.getDays(timesheet.getMese(),
														timesheet.getAnno()) + 1 + 4 + 1);
										cellGiorniTotaliPerRiga.setCellValue(giorniTotaliPerRiga);
									} else {
										formulaCell = sheet.getRow(entry.getKey())
												.getCell(EMese.getDays(timesheet.getMese(),
														timesheet.getAnno()) + 1 + 4);
										formulaCell.setCellValue(oreTotaliPerRiga);
										cellGiorniTotaliPerRiga = sheet.getRow(entry.getKey())
												.getCell(EMese.getDays(timesheet.getMese(),
														timesheet.getAnno()) + 1 + 4 + 1);
										cellGiorniTotaliPerRiga.setCellValue(giorniTotaliPerRiga);
									}
								}
							}
							oreTotaliPerMese = oreTotaliPerMese + t.getOre();
							giorniTotaliPerMese++;
						} else {
							// se non è uguale il codice commessa
							r++;
							row = sheet.createRow(r);
							giorniTotaliPerRiga++;
							mapCodiceCommessa.put(row.getRowNum(), t.getCodiceCommessa());
							mapSumCommessa.put(t.getCodiceCommessa(), t.getOre());
							mapGiorniPerRiga.put(t.getCodiceCommessa(), giorniTotaliPerRiga);
							row.createCell(0).setCellValue(cliente);
							row.createCell(1).setCellValue(trasferta);
							row.createCell(2).setCellValue(codiceCommessa);
							row.createCell(3).setCellValue(tipoCommessa);
							row.createCell(4).setCellValue(descrizioneCommessa);
							row.createCell(5 + j).setCellValue(ore);
							oreTotaliPerRiga = oreTotaliPerRiga + t.getOre();
							XSSFCell formulaCell2 = row
									.createCell(EMese.getDays(timesheet.getMese(),
											timesheet.getAnno()) + 4 + 1);
							formulaCell2.setCellValue(oreTotaliPerRiga);
							XSSFCell cellGiorniTotaliPerRiga2 = row
									.createCell(EMese.getDays(timesheet.getMese(),
											timesheet.getAnno()) + 4 + 1 + 1);
							cellGiorniTotaliPerRiga2.setCellValue(giorniTotaliPerRiga);
							if (index % 2 == 0) {
								XSSFCellStyle style2 = workbook.createCellStyle();
								XSSFFont font2 = workbook.createFont();
								style2.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
								style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
								font2.setColor(IndexedColors.WHITE.getIndex());
								style2.setFont(font2);
								int indexStyleCell = 0;
								while (indexStyleCell < EMese.getDays(timesheet.getMese(),
										timesheet.getAnno()) + 7) {
									if (row.getCell(indexStyleCell) == null) {
										row.createCell(indexStyleCell).setCellStyle(style2);
									} else {
										row.getCell(indexStyleCell).setCellStyle(style2);
									}
									indexStyleCell++;
								}

							} else {
								XSSFCellStyle style2 = workbook.createCellStyle();
								style2.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
								style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
								int indexStyleCell = 0;
								while (indexStyleCell < EMese.getDays(timesheet.getMese(), timesheet.getAnno()) + 7) {
									if (row.getCell(indexStyleCell) == null) {
										row.createCell(indexStyleCell).setCellStyle(style2);
									} else {
										row.getCell(indexStyleCell).setCellStyle(style2);
									}
									indexStyleCell++;
								}
							}
							index++;
							oreTotaliPerMese = oreTotaliPerMese + oreTotaliPerRiga;
							giorniTotaliPerMese++;
						}
					}
				}
			}

			totalRow.createCell(totalsColumnIndex).setCellValue(oreTotaliPerMese);
			totalRow.createCell(totalsColumnIndex + 1).setCellValue(giorniTotaliPerMese);

			// autoSizeColumns timesheet
			for (int columnIndex = 0; columnIndex < 50; columnIndex++) {
				sheet.autoSizeColumn(columnIndex);
			}

			// autoSiezeColumns notaSpese
			for (int columnIndex = 0; columnIndex < 50; columnIndex++) {
				secondSheet.autoSizeColumn(columnIndex);
			}

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

}