package utilities;

public interface CardFunctionsController<F,T>{

    /**
     * This method allow to not
     * have duplicates inside the array
     * @param size
     * @param array
     * the size parameter is for the size of the array
     */
     static void noDuplicates(int size,String[] array){
        int [] ar = new int [size];
        int count = 1;

        for(int i = 0; i < size; i++)  ar[i] = count++;
        count = 0;
        for(int i = 0; i < size; i ++) {
            int sm = ((int) (Math.random() * 90) + 1);
            for(int j = 0; j < size; j++) {
                if(ar[j] == sm) {
                    array[i] = String.valueOf(sm);
                    ar[j] = 0;
                    count = 1;
                }
                if(j == size - 1 && count == 0) i --;
                if(j == size - 1)count = 0;
            }
        }
    }
    //constant time used in the server side
//    static void noDuplicates(int size, String[] array) {
//        int[] tmp = new int[size];
//        int count = 1;
//
//        for (int i = 0; i < size; i++)
//            tmp[i] = count++;
//
//        count = 0;
//        int prova = 0;
//        for (int i = 0; i < size; i++) {
//            count = (int) (Math.random() * 90);
//            if (tmp[count] == (count + 1)) {
//                array[prova] = String.valueOf(count + 1);
//                tmp[count] = 0;
//                prova++;
//            }else i--;
//        }
//    }

    /**
     * Control the wins
     * @param Object
     * @param card
     */
     void controllingNumber(T Object, F [][] card);
     void controller(int value,T object);
}
