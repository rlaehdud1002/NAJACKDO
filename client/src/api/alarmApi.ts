import { IAlarm } from "atoms/Alarm.type";
import { BaseResponse, IPaging } from "atoms/Base.type";
import instance from "./clientApi";


// 알람 리스트 조회
export const getAlarm = async (
  page: number
): Promise<IPaging<IAlarm[]>> => {
  const {
    data: { success, data },
  } = await instance.get<BaseResponse<IPaging<IAlarm[]>>>(
    `/notification/searchById?page=${page}`
  );

  console.log("알람 리스트 조회 API data", data);

  if (!success) {
    throw new Error("알람 리스트 조회 실패");
  }

  return data;
};

// 알림 리스트 로드 성공
export const getAlarmReadSucess = async (): Promise<void> => {
  try {
    const {
      data: { success },
    } = await instance.post<BaseResponse<null>>("notification/readSucess");

    if (!success) {
      throw new Error("읽음 처리 완료");
    }

    console.log("postInterestbook");
  } catch (error) {
    throw new Error("읽음 처리 실패", error);
  }
};