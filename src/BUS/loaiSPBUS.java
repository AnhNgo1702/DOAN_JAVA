/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.loaiSPDAO;
import DAO.nhacungcapDAO;
import DTO.loaiSP;
import DTO.nhacungcapDTO;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class loaiSPBUS {
    private ArrayList<loaiSP> list;
    
    public loaiSPBUS(){
        list = new ArrayList<>();
        init();
    }
    
    
    private void init(){
        loaiSPDAO lDAO = new loaiSPDAO();
        list = lDAO.listLoaiSP();
    }
    
    public  ArrayList<loaiSP> getList(){
        return list;
    }
    
    
    public boolean checkTENLOAI(String t) {
        //tên nhà cung cấp không chứa số và các kí tự đặc biệt
        String regex = "^[\\p{L} ]+$";
        return t.matches(regex);
    }
    
    private String createMALOAI() {
        int max =0;
        for(int i=0;i<list.size();i++){
            String MALOAIlast = list.get(i).getMALOAI();
             String so = MALOAIlast.replaceAll("[^0-9]","");
        int stt = Integer.parseInt(so) + 1;
        if(stt > max) max = stt;
        }
        return "LOAI" + max;
      
        
    }
    
    public void add(loaiSP loaiDTO) {

        loaiDTO.setMALOAI(createMALOAI());
        list.add(loaiDTO);
        loaiSPDAO n = new loaiSPDAO();
        n.add(loaiDTO);
    }
    
    public void updateInSQL(){
        loaiSPDAO loaiDAO = new loaiSPDAO();
        for(loaiSP ncc : list){
            loaiDAO.update(ncc);
        }
    }
    
    public void delete(String MALOAI){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getMALOAI().equals(MALOAI))
                list.remove(i);
        }
    }
    
     public void deleteInSQL(String maDelete){
        loaiSPDAO loaiDAO = new  loaiSPDAO();
        loaiDAO.delete(maDelete);
    }
    
    public boolean checkNewListList(ArrayList<loaiSP> newList) {
        boolean flag = true;
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(newList.get(i))) {
                if(newList.get(i).getTENLOAI().equals("")) continue;
                if (checkTENLOAI(newList.get(i).getTENLOAI())) {
                    list.get(i).setTENLOAI(newList.get(i).getTENLOAI());
                    list.get(i).setTINHTRANG(newList.get(i).getTINHTRANG());
                }
                
                else {
                            flag = false;
                            break;
                            }
            }
        }
        return flag;
    }
}