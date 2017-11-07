/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ProjetWeekendTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VillePrefereeDetailComponent } from '../../../../../../main/webapp/app/entities/ville-preferee/ville-preferee-detail.component';
import { VillePrefereeService } from '../../../../../../main/webapp/app/entities/ville-preferee/ville-preferee.service';
import { VillePreferee } from '../../../../../../main/webapp/app/entities/ville-preferee/ville-preferee.model';

describe('Component Tests', () => {

    describe('VillePreferee Management Detail Component', () => {
        let comp: VillePrefereeDetailComponent;
        let fixture: ComponentFixture<VillePrefereeDetailComponent>;
        let service: VillePrefereeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ProjetWeekendTestModule],
                declarations: [VillePrefereeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VillePrefereeService,
                    JhiEventManager
                ]
            }).overrideTemplate(VillePrefereeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VillePrefereeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VillePrefereeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new VillePreferee(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.villePreferee).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
