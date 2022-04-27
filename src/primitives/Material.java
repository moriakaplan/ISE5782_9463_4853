package primitives;

public class Material {
    public Double3 kD;
    public Double3 kS;
    public int shininess;

    /*public Material(double kd, double ks, int shininess) {
        this.kD = kd;
        this.kS = ks;
        this.nShininess = shininess;
    }*/

    /**
     * setter for filed kD
     * @param kD
     * @return
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /*public double getkD() {
        return kD;
    }

    public double getkS() {
        return kS;
    }*/

    public Material setShininess(int nShininess) {
        this.shininess = nShininess;
        return this;
    }

    public int getShininess() {
        return shininess;
    }
}
