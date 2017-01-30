package fury.yuri.geometry;

/**
 * Created by yuri on 25/12/16.
 */
public class Pixel {

    public int x;
    public int y;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Pixel) {
            Pixel p = (Pixel) o;
            return this.x == p.x && this.y == p.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
