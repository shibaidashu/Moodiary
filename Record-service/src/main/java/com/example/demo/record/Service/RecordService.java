package com.example.demo.record.Service;

import com.example.demo.common.DTO.IntensityDTO;
import com.example.demo.common.DTO.MoodHistoryDTO;
import com.example.demo.common.Entity.Record;
import java.util.List;

public interface RecordService {

    public List<MoodHistoryDTO> getMoodHistory(int type,int userId);

    public void addRecord(Record record);
}
