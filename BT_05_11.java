class Block<T> {
    private T hangHoa;

    public void them(T hangHoa) {
        this.hangHoa = hangHoa;
    }

    public T lay() {
        return hangHoa;
    }

    public void hienThi() {
        System.out.println(hangHoa != null ? hangHoa : "Khối chứa trống");
    }
}

class Sach {
    private String ten, tacGia;

    public Sach(String ten, String tacGia) {
        this.ten = ten;
        this.tacGia = tacGia;
    }

    public String toString() {
        return "Sách: " + ten + " - " + tacGia;
    }
}

class DienThoai {
    private String hang;
    private double gia;

    public DienThoai(String hang, double gia) {
        this.hang = hang;
        this.gia = gia;
    }

    public String toString() {
        return "Điện thoại: " + hang + " - " + gia + " VNĐ";
    }
}

class ThucPham {
    private String ten, hanSuDung;

    public ThucPham(String ten, String hanSuDung) {
        this.ten = ten;
        this.hanSuDung = hanSuDung;
    }

    public String toString() {
        return "Thực phẩm: " + ten + " - HSD: " + hanSuDung;
    }
}

public class Main {
    public static void main(String[] args) {
        Block<Sach> khoSach = new Block<>();
        khoSach.them(new Sach("Java", "Nguyễn Tiến Duy"));
        khoSach.hienThi();

        Block<DienThoai> khoDienThoai = new Block<>();
        khoDienThoai.them(new DienThoai("iPhone 15 Pro", 999999999));
        khoDienThoai.hienThi();

        Block<ThucPham> khoThucPham = new Block<>();
        khoThucPham.them(new ThucPham("Bánh mì tươi", "10/11/2025"));
        khoThucPham.hienThi();
    }
}
