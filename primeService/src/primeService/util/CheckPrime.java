package primeService.util;

public class CheckPrime {
    public boolean checkPrime(int bNumber) {
        int num = bNumber;boolean flag = true;
        for (int i = 2; i <= num / 2; ++i) {
            if (num % i == 0) {
                flag = false; break;                
            }
        }return flag;
    }
}
