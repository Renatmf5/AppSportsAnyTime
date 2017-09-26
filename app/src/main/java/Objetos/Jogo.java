package Objetos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Jogo implements Serializable {

    protected int id;
    protected Date datadoEm;
    protected Time time;
    protected Endereco endereco;
    protected Lugar lugar;
    protected ArrayList<PosicaoDisponivelJogo> posicoes = new ArrayList<PosicaoDisponivelJogo>();

    public Jogo(JSONObject json) {
        try {
            if (json.has("id")) {
                this.setId(json.getInt("id"));
            }
            if (json.has("dated_at")) {
                this.setDatadoEm(this.stringToDate(
                        json.getString("dated_at"),
                        "yyyy-mm-dd kk:mm:ss"
                ));
            }
            if (json.has("place") && !json.isNull("place")) {
                this.setLugar(new Lugar(json.getJSONObject("place")));
            }
            if (json.has("team")) {
                this.setTime(new Time(json.getJSONObject("team")));
            }
            if (json.has("address")) {
                this.setEndereco(new Endereco(json.getJSONObject("address")));
            }
            if (json.has("available_positions")) {
                JSONArray posicoes = json.getJSONArray("available_positions");
                for (int i = 0; i < posicoes.length(); i++) {
                    PosicaoDisponivelJogo pos = new PosicaoDisponivelJogo(posicoes.getJSONObject(i).getString("position"));
                    this.getPosicoes().add(pos);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Jogo() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatadoEm() {
        return datadoEm;
    }

    public void setDatadoEm(Date datadoEm) {
        this.datadoEm = datadoEm;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    private Date stringToDate(String aDate,String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }

    public ArrayList<PosicaoDisponivelJogo> getPosicoes() {
        return posicoes;
    }

    public void setPosicoes(ArrayList<PosicaoDisponivelJogo> posicoes) {
        this.posicoes = posicoes;
    }

    @Override
    public String toString() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        return this.getTime().getNome() +
                ", " + sdf.format(this.getDatadoEm());
    }
}
