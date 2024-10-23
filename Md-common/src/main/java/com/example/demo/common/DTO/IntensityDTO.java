package com.example.demo.common.DTO;

import lombok.Data;
import java.util.List;

@Data
public class IntensityDTO {
    private int record_Id;
    private List<List<EmotionLabel>> data;
    @Data
    public static class EmotionLabel {
        private String label;
        private double score;
        @Override
        public String toString() {
            return "{" + "label='" + label + '\'' + ", score=" + score + '}';
        }
    }
    private String top_emotion;
    private String comfort_language;
    private String behavioral_guidance;
}
