package com.betmanager.services.interfaces;

import com.betmanager.models.entities.Bet;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface IReportService {
     ResponseEntity<byte[]> exportReportAsPdf(Page<Bet> bets);
     ResponseEntity<byte[]> exportReportAsCsv(List<Bet> bets) throws IOException;
}
