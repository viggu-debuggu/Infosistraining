import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Training, SystemReport, AuditLogEntry, PromotionCriteriaItem, ApprovalRequest } from './feature-models';

@Injectable({
  providedIn: 'root'
})
export class PlatformExtensionsService {
  private readonly baseUrl = 'http://localhost:8080/api';
  private trainingUrl = `${this.baseUrl}/trainings`;
  private reportUrl = `${this.baseUrl}/reports`;
  private auditUrl = `${this.baseUrl}/audit/logs`;
  private promotionUrl = `${this.baseUrl}/career/promotion-criteria`;
  private approvalUrl = `${this.baseUrl}/approvals`;

  constructor(private http: HttpClient) {}

  // --- Trainings ---
  getTrainings(): Observable<Training[]> {
    return this.http.get<Training[]>(this.trainingUrl);
  }

  scheduleTraining(training: Training): Observable<Training> {
    return this.http.post<Training>(this.trainingUrl, training);
  }

  enrollInTraining(id: number): Observable<Training> {
    return this.http.post<Training>(`${this.trainingUrl}/${id}/enroll`, {});
  }

  // --- System Reports ---
  getReports(): Observable<SystemReport[]> {
    return this.http.get<SystemReport[]>(this.reportUrl);
  }

  generateMilestoneReport(milestoneNumber: number): Observable<SystemReport> {
    return this.http.post<SystemReport>(`${this.reportUrl}/generate/${milestoneNumber}`, {});
  }

  // --- Audit Trail ---
  getAuditLogs(): Observable<AuditLogEntry[]> {
    return this.http.get<AuditLogEntry[]>(this.auditUrl);
  }

  createAuditLog(log: Partial<AuditLogEntry>): Observable<AuditLogEntry> {
    return this.http.post<AuditLogEntry>(this.auditUrl, log);
  }

  // --- Promotion Criteria ---
  getPromotionCriteria(): Observable<PromotionCriteriaItem[]> {
    return this.http.get<PromotionCriteriaItem[]>(this.promotionUrl);
  }

  savePromotionCriteria(criteria: Partial<PromotionCriteriaItem>): Observable<PromotionCriteriaItem> {
    return this.http.post<PromotionCriteriaItem>(this.promotionUrl, criteria);
  }

  // --- Approval Workflow Engine ---
  getApprovals(status?: string): Observable<ApprovalRequest[]> {
    const url = status ? `${this.approvalUrl}?status=${status}` : this.approvalUrl;
    return this.http.get<ApprovalRequest[]>(url);
  }

  submitApprovalRequest(request: Partial<ApprovalRequest>): Observable<ApprovalRequest> {
    return this.http.post<ApprovalRequest>(this.approvalUrl, request);
  }

  approveRequest(id: number, notes: string = 'Approved by HR Admin'): Observable<ApprovalRequest> {
    return this.http.post<ApprovalRequest>(`${this.approvalUrl}/${id}/approve`, { notes });
  }

  rejectRequest(id: number, notes: string = 'Rejected by HR Admin'): Observable<ApprovalRequest> {
    return this.http.post<ApprovalRequest>(`${this.approvalUrl}/${id}/reject`, { notes });
  }
}
