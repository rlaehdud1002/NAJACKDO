import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "components/ui/dialog";
import { useNavigate } from "react-router-dom";

interface ConfirmModalProps {
  content: string;
  open: boolean;
  setOpen: (open: boolean) => void;
  urlPath: string;
}

const ConfirmModal = ({
  content,
  open,
  setOpen,
  urlPath,
}: ConfirmModalProps) => {
  const navigate = useNavigate();
  return (
    <Dialog
      open={open}
      onOpenChange={(open) => {
        !open && setOpen(false);
      }}
    >
      <DialogContent className="bg-najackdo-background rounded-2xl">
        <DialogHeader>
          <DialogTitle />
          <DialogDescription className="h-[200px] flex flex-col items-center justify-center text-black">
            <span className="text-lg my-12">{content}</span>
            <div className="space-x-6">
              <button
                className="border-2 border-sub7 px-10 py-2 rounded-xl hover:bg-[#EBE9E7]"
                onClick={() => {
                  setOpen(false);
                }}
              >
                닫기
              </button>
              <button
                className="border-2 border-sub7 px-10 py-2 rounded-xl bg-sub7 hover:bg-[#4F473D] text-white"
                onClick={() => navigate(urlPath)}
              >
                확인
              </button>
            </div>
          </DialogDescription>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
};

export default ConfirmModal;