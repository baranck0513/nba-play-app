package com.nba.nba_play.player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="player_stats")
public class Player {
    @Id
    @Column(name = "player_id", unique = true)
    private long player_id;   // BIGINT
    private int rank;         // INT
    private String player;    // VARCHAR(100)
    private long team_id;     // BIGINT
    private String team;      // VARCHAR(10)
    private int gp;           // INT
    private double min;       // NUMERIC(5,1)
    private double fgm;       // NUMERIC(5,1)
    private double fga;       // NUMERIC(5,1)
    private double fg_pct;    // NUMERIC(6,3)
    private double fg3m;      // NUMERIC(5,1)
    private double fg3a;      // NUMERIC(5,1)
    private double fg3_pct;   // NUMERIC(6,3)
    private double ftm;       // NUMERIC(5,1)
    private double fta;       // NUMERIC(5,1)
    private double ft_pct;    // NUMERIC(6,3)
    private double oreb;      // NUMERIC(5,1)
    private double dreb;      // NUMERIC(5,1)
    private double reb;       // NUMERIC(5,1)
    private double ast;       // NUMERIC(5,1)
    private double stl;       // NUMERIC(5,1)
    private double blk;       // NUMERIC(5,1)
    private double tov;       // NUMERIC(5,1)
    private double pts;       // NUMERIC(5,1)
    private double eff;       // NUMERIC(5,1)

    public Player() {}

    public Player(long player_id, int rank, String player, long team_id, String team, int gp, double min, double fgm, double fga, double fg_pct, double fg3m, double fg3a, double fg3_pct, double ftm, double fta, double ft_pct, double oreb, double dreb, double reb, double ast, double stl, double blk, double tov, double pts, double eff) {
        this.player_id = player_id;
        this.rank = rank;
        this.player = player;
        this.team_id = team_id;
        this.team = team;
        this.gp = gp;
        this.min = min;
        this.fgm = fgm;
        this.fga = fga;
        this.fg_pct = fg_pct;
        this.fg3m = fg3m;
        this.fg3a = fg3a;
        this.fg3_pct = fg3_pct;
        this.ftm = ftm;
        this.fta = fta;
        this.ft_pct = ft_pct;
        this.oreb = oreb;
        this.dreb = dreb;
        this.reb = reb;
        this.ast = ast;
        this.stl = stl;
        this.blk = blk;
        this.tov = tov;
        this.pts = pts;
        this.eff = eff;
    }

    public long getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(long player_id) {
        this.player_id = player_id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(long team_id) {
        this.team_id = team_id;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getGp() {
        return gp;
    }

    public void setGp(int gp) {
        this.gp = gp;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getFgm() {
        return fgm;
    }

    public void setFgm(double fgm) {
        this.fgm = fgm;
    }

    public double getFga() {
        return fga;
    }

    public void setFga(double fga) {
        this.fga = fga;
    }

    public double getFg_pct() {
        return fg_pct;
    }

    public void setFg_pct(double fg_pct) {
        this.fg_pct = fg_pct;
    }

    public double getFg3m() {
        return fg3m;
    }

    public void setFg3m(double fg3m) {
        this.fg3m = fg3m;
    }

    public double getFg3a() {
        return fg3a;
    }

    public void setFg3a(double fg3a) {
        this.fg3a = fg3a;
    }

    public double getFg3_pct() {
        return fg3_pct;
    }

    public void setFg3_pct(double fg3_pct) {
        this.fg3_pct = fg3_pct;
    }

    public double getFtm() {
        return ftm;
    }

    public void setFtm(double ftm) {
        this.ftm = ftm;
    }

    public double getFta() {
        return fta;
    }

    public void setFta(double fta) {
        this.fta = fta;
    }

    public double getFt_pct() {
        return ft_pct;
    }

    public void setFt_pct(double ft_pct) {
        this.ft_pct = ft_pct;
    }

    public double getOreb() {
        return oreb;
    }

    public void setOreb(double oreb) {
        this.oreb = oreb;
    }

    public double getDreb() {
        return dreb;
    }

    public void setDreb(double dreb) {
        this.dreb = dreb;
    }

    public double getReb() {
        return reb;
    }

    public void setReb(double reb) {
        this.reb = reb;
    }

    public double getAst() {
        return ast;
    }

    public void setAst(double ast) {
        this.ast = ast;
    }

    public double getStl() {
        return stl;
    }

    public void setStl(double stl) {
        this.stl = stl;
    }

    public double getBlk() {
        return blk;
    }

    public void setBlk(double blk) {
        this.blk = blk;
    }

    public double getTov() {
        return tov;
    }

    public void setTov(double tov) {
        this.tov = tov;
    }

    public double getPts() {
        return pts;
    }

    public void setPts(double pts) {
        this.pts = pts;
    }

    public double getEff() {
        return eff;
    }

    public void setEff(double eff) {
        this.eff = eff;
    }
}
