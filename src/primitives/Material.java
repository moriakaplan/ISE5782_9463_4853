package primitives;

public class Material {
    public Double3 kD = new Double3(0);
    public Double3 kS = new Double3(0);
    public Double3 kT = new Double3(0);
    public Double3 kR = new Double3(0);
    public int shininess = 0;

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

    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }
    public Material setKr(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    public Material setKr(Double3 kR) {
        this.kR = kR;
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
