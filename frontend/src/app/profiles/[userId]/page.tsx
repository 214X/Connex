// src/app/profiles/[userId]/page.tsx
import { getProfileByUserId } from "@/lib/api/profile/profile.api";
import { ProfilePage } from "@/modules/profile";
import { notFound } from "next/navigation";

type PageProps = {
    params: Promise<{
        userId: string;
    }>;
};

export default async function ProfilePageRoute({ params }: PageProps) {
    const { userId } = await params; // ✅ KRİTİK SATIR

    const numericUserId = Number(userId);
    if (Number.isNaN(numericUserId)) {
        notFound();
    }

    try {
        const res = await getProfileByUserId(numericUserId);

        if (!res.success || !res.data) {
            notFound();
        }

        return <ProfilePage profile={res.data} />;
    } catch {
        notFound();
    }
}
