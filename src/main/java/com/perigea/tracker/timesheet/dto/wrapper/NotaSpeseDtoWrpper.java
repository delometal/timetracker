package com.perigea.tracker.timesheet.dto.wrapper;

import java.io.Serializable;

import com.perigea.tracker.timesheet.dto.NotaSpeseDto;
import com.perigea.tracker.timesheet.entity.keys.TimesheetEntryKey;

import lombok.Data;

@Data
public class NotaSpeseDtoWrpper implements Serializable {

		private static final long serialVersionUID = -8392790428900594810L;
		
		private NotaSpeseDto notaSpeseDto;
		private TimesheetEntryKey id;


	}
