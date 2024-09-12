package com.betmanager.services.interfaces;

import com.betmanager.models.entities.Bet;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReportService {
     ResponseEntity<byte[]> exportReportAsPdf(List<Bet> bets);
}
