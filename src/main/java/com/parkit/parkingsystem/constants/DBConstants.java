package com.parkit.parkingsystem.constants;

/**
 * Cette classe contient toutes les constantes MySQL pour communiquer avec la base de données
 */
public class DBConstants {
    /**
     * cette constante permet d'avoir la prochaine place de paring disponible
     */
    public static final String GET_NEXT_PARKING_SPOT = "select "
            + "min(PARKING_NUMBER) from parking where AVAILABLE = true and"
            + " TYPE = ?";
    /**
     * cette constante permet de mettre a jour la disponibilité d'un place de parking
     */
    public static final String UPDATE_PARKING_SPOT =
            "update parking set available = ?"
                    + " where PARKING_NUMBER = ?";
    public static final String GET_PARKING_SPOT = "select * "
            + "from where PARKING_NUMBER=?";
    /**
     * cette constante permet d'enregistrer un ticket avec la plaque d'immatriculation du vehicule entrant
     */
    public static final String SAVE_TICKET =
            "insert into ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, "
                    + "IN_TIME, OUT_TIME) values(?,?,?,?,?)";
    /**
     * cette constante permet de mettre a jour un ticket en y ajoutant l'heure de sortie du véhicule
     */
    public static final String UPDATE_TICKET = "update ticket set PRICE=?, "
            + "OUT_TIME=? where ID=?";
    /**
     * cette constante permet d'avoir un ticket avec toutes les infos sur un véhicule sortant du parking
     */
    public static final String GET_TICKET = "select t.PARKING_NUMBER, t.ID, "
            + "t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE from ticket t,parking p"
            + " where p.parking_number = t.parking_number and "
            + "t.VEHICLE_REG_NUMBER=? and t.OUT_TIME is null order "
            + "by t.IN_TIME  limit 1";
    /**
     * cette constante permet de compter le nombre de fois qu'un véhicule est entré dans le parking afin de pouvoir
     * lui attribuer le statu d'utilisateur récurrent et de ui faire bénéficier d'une réduction
     */
    public static final String COUNT_VEHICLE_REG_NUMBER = "SELECT count(*) "
            + "FROM ticket t, parking p where t.VEHICLE_REG_NUMBER =?"
            + " and p.TYPE = ? ";
    /**
     * Cette constante permet de s'assurer que le véhicule n'est pas deja dans le parking
     */
    public static final String VERIFY_VEHICLE_REG_NUMBER = "SELECT count(*) "
            + "FROM ticket t, parking p where t.VEHICLE_REG_NUMBER = ? and "
            + "p.TYPE = ? and t.OUT_TIME is null";

}
