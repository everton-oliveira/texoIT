package br.com.texo.teste.dto;

import br.com.texo.teste.helper.ProducerIntervalWin;

import java.io.Serializable;
import java.util.List;

public class WinIntervalDTO implements Serializable {

    private List<ProducerIntervalWin> min;
    private List<ProducerIntervalWin> max;

    public List<ProducerIntervalWin> getMin() {
        return min;
    }

    public void setMin(List<ProducerIntervalWin> min) {
        this.min = min;
    }

    public List<ProducerIntervalWin> getMax() {
        return max;
    }

    public void setMax(List<ProducerIntervalWin> max) {
        this.max = max;
    }
}
